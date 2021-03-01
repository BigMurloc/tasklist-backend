CREATE TABLE task
(
    id          BIGINT NOT NULL,
    creator     BIGINT NOT NULL,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(450),
    timestamp   TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_id
        FOREIGN KEY (creator)
            REFERENCES "user" (id)
);