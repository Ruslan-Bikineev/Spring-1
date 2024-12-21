CREATE TABLE IF NOT EXISTS users (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP default now()::TIMESTAMP(2)
);

INSERT INTO users (name) VALUES ('Egor');
INSERT INTO users (name) VALUES ('Igor');
INSERT INTO users (name) VALUES ('Peter');
INSERT INTO users (name) VALUES ('Pavel');
INSERT INTO users (name) VALUES ('Martin');
