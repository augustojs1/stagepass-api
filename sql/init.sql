CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE EXTENSION IF NOT EXISTS postgis;

-- users
create table users (
       id UUID primary key default gen_random_uuid(),
       first_name VARCHAR(50) NOT NULL,
       last_name VARCHAR(50) NOT NULL,
       email VARCHAR(50) unique NOT NULL,
       "password" VARCHAR(255) NOT NULL,
       is_admin BOOLEAN DEFAULT FALSE,
       created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE index users_email_idx on users (email);

-- organizations
create table organizations (
    id UUID primary key default gen_random_uuid(),
    user_id UUID REFERENCES users(id),
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    contact_email VARCHAR(50) NOT NULL,
    website_url TEXT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- categories
create table event_categories (
   id UUID primary key default gen_random_uuid(),
   name VARCHAR(100) unique not NULL,
   updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
   created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO event_categories (name)
VALUES
    ('Music'),
    ('Sport'),
    ('Exhibition'),
    ('Business'),
    ('Photography'),
    ('Theater'),
    ('Comedy'),
    ('Cinema'),
    ('Education'),
    ('Gaming'),
    ('Festival');

create index event_categories_name_idx on event_categories (name);