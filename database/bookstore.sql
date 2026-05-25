CREATE DATABASE IF NOT EXISTS Bookstore
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE Bookstore;

CREATE TABLE IF NOT EXISTS book (
    book_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(128) NOT NULL,
    author VARCHAR(45) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (book_id),
    UNIQUE KEY book_id_UNIQUE (book_id),
    UNIQUE KEY title_UNIQUE (title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO book (title, author, price) VALUES
    ('Effective Java', 'Joshua Bloch', 220.00),
    ('Clean Code', 'Robert C. Martin', 185.50),
    ('Java: Como Programar', 'Deitel', 310.90)
ON DUPLICATE KEY UPDATE
    author = VALUES(author),
    price = VALUES(price);
