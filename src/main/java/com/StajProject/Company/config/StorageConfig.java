package com.StajProject.Company.config;

import com.StajProject.Company.model.PropertiesData;
import com.StajProject.Company.repository.PropertiesDataRepository;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class StorageConfig {

    private final PropertiesDataRepository propertiesDataRepository;

    @Bean
    public AmazonS3 amazonS3Client() {
        List<PropertiesData> response = propertiesDataRepository.findAll();
        PropertiesData propertiesData = response.getFirst();

        AWSCredentials credentials = new BasicAWSCredentials(propertiesData.getAwsAccessKey(), propertiesData.getAwsSecretKey());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(propertiesData.getAwsRegion())
                .build();
    }

}