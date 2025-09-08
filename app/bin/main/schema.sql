-- This script ensures tables exist before data.sql runs
-- It will be executed before data.sql due to Spring Boot's default ordering

-- First, drop all tables if they exist (in the correct order to handle foreign keys)
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS credit_cards;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS messages;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Create tables with explicit schema definition
CREATE TABLE IF NOT EXISTS messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    timestamp DATETIME(6) NOT NULL,
    content VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    balance DECIMAL(19,2) NOT NULL,
    creation_date DATETIME(6) NOT NULL,
    account_number VARCHAR(255) NOT NULL UNIQUE,
    account_type VARCHAR(255) NOT NULL,
    owner_id VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS credit_cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    balance DECIMAL(19,2) NOT NULL,
    credit_limit DECIMAL(19,2) NOT NULL,
    status BIT NOT NULL,
    card_name VARCHAR(255) NOT NULL,
    card_type VARCHAR(255) NOT NULL,
    last_four_digits VARCHAR(255) NOT NULL,
    owner_id VARCHAR(255) NOT NULL,
    valid_until VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    credit_card_id BIGINT NOT NULL,
    transaction_date DATETIME(6) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    FOREIGN KEY (credit_card_id) REFERENCES credit_cards(id)
) ENGINE=InnoDB;
