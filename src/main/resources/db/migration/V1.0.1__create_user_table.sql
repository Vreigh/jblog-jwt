CREATE TABLE user
(
    id        BIGINT(20)   NOT NULL AUTO_INCREMENT,
    email     VARCHAR(100) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    join_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    UNIQUE KEY UK_user_email (email)
);
