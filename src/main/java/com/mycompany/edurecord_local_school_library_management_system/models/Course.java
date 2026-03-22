package com.mycompany.edurecord_local_school_library_management_system.models;

/**
 * Supported courses for the EduRecord system.
 * 
 * @author Antigravity
 */
public enum Course {
    BSED("Bachelor of Secondary Education"),
    BSBA("Bachelor of Science in Business Administration"),
    BSIT("Bachelor of Science in Information Technology"),
    NONE("Not Applicable"); // For Admin/Librarian roles

    private final String fullName;

    Course(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
