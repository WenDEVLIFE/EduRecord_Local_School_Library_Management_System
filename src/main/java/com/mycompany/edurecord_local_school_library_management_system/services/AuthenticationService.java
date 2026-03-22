package com.mycompany.edurecord_local_school_library_management_system.services;

import com.mycompany.edurecord_local_school_library_management_system.models.User;
import com.mycompany.edurecord_local_school_library_management_system.models.Course;
import com.mycompany.edurecord_local_school_library_management_system.repositories.DatabaseConnection;
import com.mycompany.edurecord_local_school_library_management_system.repositories.ActivityLogRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service for handling user authentication via MySQL database.
 * 
 * @author Antigravity
 */
public class AuthenticationService {

    /**
     * Authenticates a user based on username and password using the database.
     * 
     * @param username the username
     * @param password the password
     * @return User object if successful, null otherwise
     */
    public User authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // Note: In a real app, hash passwords!

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setRole(rs.getString("role"));

                    String courseStr = rs.getString("course");
                    if (courseStr != null && !courseStr.isEmpty() && !courseStr.equals("NONE")) {
                        try {
                            user.setCourse(Course.valueOf(courseStr));
                        } catch (IllegalArgumentException e) {
                            user.setCourse(Course.NONE);
                        }
                    } else {
                        user.setCourse(Course.NONE);
                    }

                    // Set session globally
                    SessionManager.setCurrentUser(user);

                    // Log the activity
                    new ActivityLogRepository().logActivity(user.getUsername(), "Login", "Successful system access.");

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Invalid credentials
    }
}
