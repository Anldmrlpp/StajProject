CREATE SCHEMA IF NOT EXISTS util_sch;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS util_sch.config_properties
(
    id                  uuid DEFAULT uuid_generate_v4(),
    adminSignUpKey                      VARCHAR NOT NULL,
    awsAccessKey                      VARCHAR NOT NULL,
    awsSecretKey                      VARCHAR NOT NULL,
    awsS3BucketName                   VARCHAR NOT NULL,
    awsRegion                         VARCHAR NOT NULL,
    awsCdnPath                        VARCHAR NOT NULL,
    PRIMARY KEY (id)
);