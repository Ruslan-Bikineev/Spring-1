CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP default now()::TIMESTAMP(2)
);

CREATE TABLE IF NOT EXISTS phones
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id),
    number  VARCHAR(12) NOT NULL
);