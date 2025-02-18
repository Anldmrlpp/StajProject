CREATE SCHEMA IF NOT EXISTS util_sch;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS util_sch.employee_data
(
    id                  uuid DEFAULT uuid_generate_v4(),
    first_name                 VARCHAR NOT NULL,
    last_name                  VARCHAR NOT NULL,
    email                      VARCHAR NOT NULL UNIQUE,
    password                   VARCHAR NOT NULL,
    image_url                  VARCHAR UNIQUE,
    biography                  VARCHAR,
    phone_number               VARCHAR,
    department                 VARCHAR,
    birthday                   DATE,
    leave_balance              INT NOT NULL,
    statuses                   VARCHAR NOT NULL,
    created_date               DATE NOT NULL,
    updated_date               DATE NOT NULL,
    PRIMARY KEY (id)
);