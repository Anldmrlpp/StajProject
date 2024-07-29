CREATE SCHEMA IF NOT EXISTS util_sch;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS util_sch.admin_data
(
    id                  uuid DEFAULT uuid_generate_v4(),
    first_name               VARCHAR NOT NULL,
    last_name                VARCHAR NOT NULL,
    email                    VARCHAR UNIQUE NOT NULL,
    password                 VARCHAR NOT NULL,
    image_url                VARCHAR UNIQUE,
    phone_number             VARCHAR,
    role                     VARCHAR,
    created_date             DATE NOT NULL,
    updated_date             DATE NOT NULL,
    PRIMARY KEY (id)
);