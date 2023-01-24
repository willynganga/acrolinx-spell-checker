CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    password VARCHAR(255),
    username VARCHAR(255),
    trials   INT,
    locked   BOOLEAN,
    CONSTRAINT pk_users PRIMARY KEY (id)
);