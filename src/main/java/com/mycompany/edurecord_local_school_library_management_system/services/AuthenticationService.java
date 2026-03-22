package com.mycompany.edurecord_local_school_library_management_system.services;

import com.mycompany.edurecord_local_school_library_management_system.models.User;
import com.mycompany.edurecord_local_school_library_management_system.models.Course;

/**
 * Service for handling user authentication.
 * 
 * @author Antigravity
 */
public class AuthenticationService {

    /**
     * Authenticates a user based on username and password.
     * Currently uses mock data for initial setup.
     * 
     * @param username the username
     * @param password the password
     * @return User object if successful, null otherwise
     */
    public User authenticate(String username, String password) {
        // Mock authentication for initial setup
        // Replace with MySQL verification in the next phase
        if ("admin".equals(username) && "admin123".equals(password)) {
            return new User(1, "admin", "admin123", "System", "Admin", "ADMIN", Course.NONE);
        } else if ("librarian".equals(username) && "lib123".equals(password)) {
            return new User(2, "librarian", "lib123", "Library", "Staff", "LIBRARIAN", Course.NONE);
        }

        return null;
    }
}
