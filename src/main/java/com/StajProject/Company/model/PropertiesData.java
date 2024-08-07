package com.StajProject.Company.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Entity
@Data
@Table(name = "config_properties", schema = "util_sch")
public class PropertiesData {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "adminsignupkey")
    private String adminSignUpKey;
    @Column(name = "awsaccesskey")
    private String awsAccessKey;
    @Column(name = "awssecretkey")
    private String awsSecretKey;
    @Column(name = "awss3bucketname")
    private String awsS3BucketName;
    @Column(name = "awsregion")
    private String awsRegion;
    @Column(name = "awscdnpath")
    private String awsCdnPath;

}