CREATE TABLE authority
(
    id        BIGINT,
    authority VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE user_authority
(
    user_id      BIGINT,
    authority_id BIGINT,

    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES "user" (id),
    CONSTRAINT fk_authority_id
        FOREIGN KEY (authority_id)
            REFERENCES authority (id)
);