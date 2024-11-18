CREATE TABLE wallets
(
    id          VARCHAR(36) NOT NULL PRIMARY KEY,
    balance     DOUBLE      NOT NULL,
    customer_id VARCHAR(36) NOT NULL
);