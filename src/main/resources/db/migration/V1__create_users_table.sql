-- V1__create_users_table.sql
CREATE TYPE USER_ROLE AS ENUM ('OWNER', 'HOST');

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(180) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    role USER_ROLE NOT NULL,
    avatar_url VARCHAR(255),
    rating NUMERIC(3, 2) NOT NULL DEFAULT 0.0,
    active BOOLEAN NOT NULL DEFAULT TRUE
);