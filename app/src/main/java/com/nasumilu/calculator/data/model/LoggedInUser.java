package com.nasumilu.calculator.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String token;

    public LoggedInUser(String userId, String displayName, String token) {
        this.userId = userId;
        this.displayName = displayName;
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}