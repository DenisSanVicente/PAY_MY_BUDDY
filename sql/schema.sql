CREATE DATABASE IF NOT EXISTS pay_my_buddy;

USE pay_my_buddy;

CREATE TABLE user (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    email VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE account (
    id_account INT AUTO_INCREMENT PRIMARY KEY,
    balance DECIMAL(10,2) NOT NULL,
    user_id INT NOT NULL UNIQUE,
    CONSTRAINT fk_account_user
        FOREIGN KEY (user_id)
        REFERENCES user(id_user)
);

CREATE TABLE connection (
    id_connection INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    connection_id INT NOT NULL,
    CONSTRAINT fk_connection_user
        FOREIGN KEY (user_id)
        REFERENCES user(id_user),
    CONSTRAINT fk_connection_contact
        FOREIGN KEY (connection_id)
        REFERENCES user(id_user)
);

CREATE TABLE transaction (
    id_transaction INT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10,2) NOT NULL,
    description VARCHAR(255),
    date_transaction DATETIME NOT NULL,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    CONSTRAINT fk_transaction_sender
        FOREIGN KEY (sender_id)
        REFERENCES user(id_user),
    CONSTRAINT fk_transaction_receiver
        FOREIGN KEY (receiver_id)
        REFERENCES user(id_user)
);