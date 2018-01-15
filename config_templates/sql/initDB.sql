DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS city CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS user_group CASCADE;
DROP SEQUENCE IF EXISTS user_seq;
DROP TYPE IF EXISTS user_flag;
DROP TYPE IF EXISTS group_type;
DROP SEQUENCE IF EXISTS common_seq;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');

CREATE SEQUENCE common_seq START 100000;

CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  full_name TEXT NOT NULL,
  email     TEXT NOT NULL,
  flag      user_flag NOT NULL
);

CREATE TABLE city (
  id INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  ref TEXT NOT NULL,
  value TEXT NOT NULL
);

ALTER TABLE users ADD COLUMN city_id INTEGER REFERENCES city(id);

CREATE TABLE project (
  id INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  name TEXT NOT NULL,
  description TEXT
);

CREATE TYPE group_type AS ENUM ('finished', 'current');

CREATE TABLE groups (
  id INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  name TEXT NOT NULL,
  type group_type NOT NULL,
  project_id INTEGER REFERENCES project(id)
);

CREATE TABLE user_group (
  user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE ,
  group_id INTEGER NOT NULL REFERENCES groups(id)
);