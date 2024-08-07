CREATE SCHEMA IF NOT EXISTS util_sch;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS util_sch.permission_data
(
    id                  uuid DEFAULT uuid_generate_v4(),
    email                       VARCHAR NOT NULL,
    description                 VARCHAR NOT NULL,
    number_of_days              INT NOT NULL,
    start_date                  DATE NOT NULL,
    end_date                    DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (email) REFERENCES util_sch.employee_data(email)
);