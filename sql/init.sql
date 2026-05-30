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

-- events
create table events (
    id UUID primary key default gen_random_uuid() NOT NULL,
    organization_id UUID references organizations (id) not NULL,
    event_category_id UUID references event_categories (id) NOT NULL,
    is_free BOOLEAN NOT NULL,
    name VARCHAR(100) NOT NULL,
    description text NOT NULL,
    banner_url text,
    slug TEXT NOT NULL,
    address_street VARCHAR(100) NOT NULL,
    address_number VARCHAR(20) NOT NULL,
    address_district VARCHAR(100) NOT NULL,
    address_city VARCHAR(100) NOT NULL,
    country VARCHAR(50) NOT NULL,
    location GEOGRAPHY(Point, 4326) NOT NULL,
    timezone VARCHAR(100) NOT NULL,
    sales_starts_at TIMESTAMPTZ NOT NULL,
    starts_at TIMESTAMPTZ NOT NULL,
    ends_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT starts_at_before_ends_at check (ends_at > starts_at),
    CONSTRAINT sales_starts_at_before_event_starts_at check (starts_at > sales_starts_at)
);

CREATE INDEX events_name_trgm_idx ON events USING gin (name gin_trgm_ops);
CREATE INDEX events_starts_at_idx on events(starts_at);
CREATE INDEX events_slug_idx ON events(slug);

-- event_tickets
create table event_tickets (
   id UUID primary key default gen_random_uuid() not null,
   event_id UUID references events (id) not null,
   name VARCHAR(50) not null,
   price BIGINT not null,
   amount INT not null,
   sold boolean default false,
   updated_at TIMESTAMPTZ default CURRENT_TIMESTAMP,
   created_at TIMESTAMPTZ default CURRENT_TIMESTAMP,

   CONSTRAINT non_zero_price check (price > 0)
);

-- event_images
CREATE TABLE event_images (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  event_id UUID REFERENCES events(id) NOT NULL,
  url TEXT NOT NULL,
  object_key TEXT NOT NULL,
  created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_event_images_event_id ON event_images(event_id);