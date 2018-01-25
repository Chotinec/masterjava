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