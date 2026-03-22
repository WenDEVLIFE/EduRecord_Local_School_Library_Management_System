package com.mycompany.edurecord_local_school_library_management_system.repositories;

import com.mycompany.edurecord_local_school_library_management_system.models.ActivityLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for reading and writing system activity logs.
 * 
 * @author Antigravity
 */
public class ActivityLogRepository {

    /**
     * Inserts a new activity log into the database.
     */
    public void logActivity(String username, String actionType, String details) {
        String query = "INSERT INTO activity_logs (username, action_type, details) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, actionType);
            stmt.setString(3, details);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the 15 most recent activity logs for the dashboard.
     */
    public List<ActivityLog> getRecentActivities() {
        List<ActivityLog> logs = new ArrayList<>();
        String query = "SELECT * FROM activity_logs ORDER BY created_at DESC LIMIT 15";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ActivityLog log = new ActivityLog();
                log.setId(rs.getInt("id"));
                log.setUsername(rs.getString("username"));
                log.setActionType(rs.getString("action_type"));
                log.setDetails(rs.getString("details"));
                log.setCreatedAt(rs.getTimestamp("created_at"));
                logs.add(log);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}
