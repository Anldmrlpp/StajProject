CREATE SCHEMA IF NOT EXISTS util_sch;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOTd EXISTS util_sch.admin_data
(
    id                  uuid DEFAULT uuid_generate_v4(),
    email                    VARCHAR UNIQUE NOT NULL,
    password                 VARCHAR NOT NULL,
    role                     VARCHAR NOT NULL,
    PRIMARY KEY (id)
);
