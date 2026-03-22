-- EduRecord: Peñaranda Off-Campus Library System
-- Database Schema

CREATE DATABASE IF NOT EXISTS edurecord_db;
USE edurecord_db;

-- Table: users
-- Stores Admins, Librarians, and Students
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'LIBRARIAN', 'STUDENT') NOT NULL,
    course ENUM('BSED', 'BSBA', 'BSIT', 'NONE') DEFAULT 'NONE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: books
-- Stores the library inventory
CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    quantity INT DEFAULT 1,
    category VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: transactions
-- Tracks borrowing and returning of books
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    status ENUM('BORROWED', 'RETURNED') DEFAULT 'BORROWED',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

-- Initial Mock Data
INSERT INTO users (username, password, first_name, last_name, role, course) VALUES
('admin', 'admin123', 'System', 'Admin', 'ADMIN', 'NONE'),
('librarian', 'lib123', 'Library', 'Staff', 'LIBRARIAN', 'NONE');
