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

INSERT INTO users (name)
VALUES ('Egor');
INSERT INTO users (name)
VALUES ('Igor');
INSERT INTO users (name)
VALUES ('Peter');
INSERT INTO users (name)
VALUES ('Pavel');
INSERT INTO users (name)
VALUES ('Martin');


INSERT INTO phones (user_id, number)
VALUES (1, '+7927000000');
INSERT INTO phones (user_id, number)
VALUES (1, '+7927000001');
INSERT INTO phones (user_id, number)
VALUES (1, '+7927000003');
INSERT INTO phones (user_id, number)
VALUES (2, '+7927000000');
INSERT INTO phones (user_id, number)
VALUES (2, '+7927000004');
INSERT INTO phones (user_id, number)
VALUES (3, '+7927000004');
INSERT INTO phones (user_id, number)
VALUES (4, '+7927000005');
INSERT INTO phones (user_id, number)
VALUES (5, '8927000005');