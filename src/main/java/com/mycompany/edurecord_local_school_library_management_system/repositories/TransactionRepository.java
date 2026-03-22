package com.mycompany.edurecord_local_school_library_management_system.repositories;

import java.sql.*;

public class TransactionRepository {

    public int getTotalTransactionsCount() {
        String query = "SELECT COUNT(*) FROM transactions";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getOverdueBooksCount() {
        // Simplified query assuming direct date comparison or status checks
        // Currently relying on hardcoded mock for overdue if no explicit date logic
        // exists.
        // As a placeholder let's just return 0 for now until actual date logic is
        // implemented.
        return 0;
    }

    public int getBorrowedBooksCount() {
        String query = "SELECT COUNT(*) FROM transactions WHERE status = 'BORROWED'";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
