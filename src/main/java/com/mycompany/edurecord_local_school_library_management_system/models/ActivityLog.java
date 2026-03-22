package com.mycompany.edurecord_local_school_library_management_system.models;

import java.sql.Timestamp;

/**
 * Model representing a system activity log.
 * 
 * @author Antigravity
 */
public class ActivityLog {
    private int id;
    private String username;
    private String actionType;
    private String details;
    private Timestamp createdAt;

    public ActivityLog() {
    }

    public ActivityLog(String username, String actionType, String details) {
        this.username = username;
        this.actionType = actionType;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
