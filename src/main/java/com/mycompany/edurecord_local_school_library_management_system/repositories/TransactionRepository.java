package com.mycompany.edurecord_local_school_library_management_system.repositories;

import com.mycompany.edurecord_local_school_library_management_system.models.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        // Using JOIN to get username and book title for the UI
        String query = "SELECT t.*, u.username, b.title as book_title " +
                "FROM transactions t " +
                "JOIN users u ON t.user_id = u.id " +
                "JOIN books b ON t.book_id = b.id " +
                "ORDER BY t.id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("user_id"));
                t.setBookId(rs.getInt("book_id"));
                t.setBorrowDate(rs.getDate("borrow_date"));
                t.setReturnDate(rs.getDate("return_date"));
                t.setStatus(rs.getString("status"));
                t.setUsername(rs.getString("username"));
                t.setBookTitle(rs.getString("book_title"));
                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public boolean borrowBook(int userId, int bookId) {
        // 1. Check if book exists and has quantity > 0
        String checkQuery = "SELECT quantity FROM books WHERE id = ?";
        String updateBookQuery = "UPDATE books SET quantity = quantity - 1 WHERE id = ?";
        String insertTransQuery = "INSERT INTO transactions (user_id, book_id, borrow_date, status) VALUES (?, ?, CURDATE(), 'BORROWED')";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            int currentQty = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, bookId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        currentQty = rs.getInt("quantity");
                    } else {
                        return false; // Book not found
                    }
                }
            }

            if (currentQty <= 0) {
                conn.rollback();
                return false; // Not enough quantity
            }

            // 2. Deduct quantity
            try (PreparedStatement updateStmt = conn.prepareStatement(updateBookQuery)) {
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
            }

            // 3. Create transaction record
            try (PreparedStatement insertStmt = conn.prepareStatement(insertTransQuery)) {
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, bookId);
                insertStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public boolean returnBook(int transactionId, int bookId) {
        String updateTransQuery = "UPDATE transactions SET status = 'RETURNED', return_date = CURDATE() WHERE id = ? AND status = 'BORROWED'";
        String updateBookQuery = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Update transaction
            int updatedRows = 0;
            try (PreparedStatement updateTransStmt = conn.prepareStatement(updateTransQuery)) {
                updateTransStmt.setInt(1, transactionId);
                updatedRows = updateTransStmt.executeUpdate();
            }

            if (updatedRows == 0) {
                conn.rollback();
                return false; // Transaction not found or already returned
            }

            // 2. Add back to inventory
            try (PreparedStatement updateBookStmt = conn.prepareStatement(updateBookQuery)) {
                updateBookStmt.setInt(1, bookId);
                updateBookStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public int getTotalTransactionsCount() {
        String query = "SELECT COUNT(*) FROM transactions";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getBorrowedBooksCount() {
        String query = "SELECT COUNT(*) FROM transactions WHERE status = 'BORROWED'";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getOverdueBooksCount() {
        return 0; // Simplified for now
    }
}
