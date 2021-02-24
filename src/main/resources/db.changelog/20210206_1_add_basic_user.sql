DROP TABLE IF EXISTS test;
CREATE TABLE "user"
(
    id       BIGINT,
    username VARCHAR(16) UNIQUE,
    password VARCHAR(255),
    PRIMARY KEY (id)
);
