package com.StajProject.Company.service.impl;

import com.StajProject.Company.exception.ErrorMessages;
import com.StajProject.Company.exception.PermissionException;
import com.StajProject.Company.model.FileEntity;
import com.StajProject.Company.model.PropertiesData;
import com.StajProject.Company.repository.FileRepository;
import com.StajProject.Company.repository.PropertiesDataRepository;
import com.StajProject.Company.service.FileService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository repository;
    private final AmazonS3 amazonS3Client;

    @Value("${file.allowed-formats}")
    private String[] allowedFormats;
    @Value("${file.default-image-height}")
    private int defaultImageHeight;
    @Value("${file.default-image-width}")
    private int defaultImageWidth;

    private final PropertiesDataRepository propertiesDataRepository;

    private String bucketName;
    private String cdnPath;

    @PostConstruct
    public void init() {
        List<PropertiesData> response = propertiesDataRepository.findAll();

        if (response.isEmpty()) {
            throw PermissionException.withStatusAndMessage(HttpStatus.BAD_REQUEST, ErrorMessages.CONFIG_PROPERTIES_NOT_FOUND);
        } else {
            PropertiesData propertiesData = response.getFirst();
            this.bucketName = propertiesData.getAwsS3BucketName();
            this.cdnPath = propertiesData.getAwsCdnPath();
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String randomName = UUID.randomUUID().toString().replace("-", "");
        String fileType = Objects.requireNonNull(file.getContentType()).split("/")[1];

        if(!Arrays.asList(allowedFormats).contains(fileType)) {
            throw PermissionException.withStatusAndMessage(HttpStatus.BAD_REQUEST, ErrorMessages.UNSUPPORTED_FILE_TYPE);
        }

        String fileName = randomName + "." + fileType;

        try {
            File tempFile;

            if(fileType.equals("png") || fileType.equals("jpeg") || fileType.equals("jpg")) {
                BufferedImage originalImage = ImageIO.read(file.getInputStream());
                BufferedImage resizedImage = new BufferedImage(defaultImageWidth, defaultImageHeight, originalImage.getType());

                Graphics2D writer = resizedImage.createGraphics();
                writer.drawImage(originalImage, 0, 0, defaultImageWidth, defaultImageHeight, null);
                writer.dispose();

                tempFile = File.createTempFile(randomName, "." + fileType);
                ImageIO.write(resizedImage, fileType, tempFile);
            }
            else {
                tempFile = File.createTempFile(randomName, "." + fileType);
                file.transferTo(tempFile);
            }

            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, tempFile));

            tempFile.delete();

            FileEntity fileEntity = FileEntity.builder()
                    .name(fileName)
                    .type(file.getContentType())
                    .cdnPath(cdnPath)
                    .build();

            FileEntity responseFile = repository.save(fileEntity);

            return responseFile.getCdnPath() + "/" + responseFile.getName();

        } catch (IOException e) {
            throw PermissionException.withStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.FILE_CANNOT_WRITE);
        }

    }

    @Override
    public java.util.List<String> uploadFiles(java.util.List<MultipartFile> files) {
        CompletableFuture<java.util.List<String>> uploadResultFuture = uploadFilesAsync(files);

        try {
            return uploadResultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw PermissionException.withStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.FILE_CANNOT_WRITE);
        }

    }

    @Async
    public CompletableFuture<java.util.List<String>> uploadFilesAsync(java.util.List<MultipartFile> files) {
        List<String> uploadedFileNames = new ArrayList<>();

        files.forEach(file -> {
            uploadedFileNames.add(uploadFile(file));
        });

        return CompletableFuture.completedFuture(uploadedFileNames);
    }

    @Override
    public void deleteFile(String fileName) {

        String splitFileName = fileName.split(cdnPath + "/")[1];
        Optional<FileEntity> response = repository.findByName(splitFileName);

        if(response.isPresent()){
            FileEntity existFile = response.get();

            try {
                repository.delete(existFile);
                amazonS3Client.deleteObject(bucketName, splitFileName);
            } catch (Exception e) {
                throw PermissionException.withStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.FILE_CANNOT_DELETE);
            }

        }
        else {
            throw PermissionException.withStatusAndMessage(HttpStatus.NOT_FOUND, ErrorMessages.FILE_NOT_FOUND);
        }

    }

}