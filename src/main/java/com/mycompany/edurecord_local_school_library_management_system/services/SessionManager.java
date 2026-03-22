package com.mycompany.edurecord_local_school_library_management_system.services;

import com.mycompany.edurecord_local_school_library_management_system.models.User;

/**
 * Manages the current user session across the application.
 * 
 * @author Antigravity
 */
public class SessionManager {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
