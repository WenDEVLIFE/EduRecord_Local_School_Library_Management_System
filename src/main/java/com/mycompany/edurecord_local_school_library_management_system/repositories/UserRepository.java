package com.mycompany.edurecord_local_school_library_management_system.repositories;

import java.sql.*;

public class UserRepository {

    public int getTotalActiveUsers() {
        String query = "SELECT COUNT(*) FROM users";
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
