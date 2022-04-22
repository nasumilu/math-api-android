package com.nasumilu.calculator.data.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String token;

    public LoggedInUser(String token) throws JSONException {
        JSONObject json = new JSONObject(token);
        this.token = json.getString("access_token");
        var payload = new JSONObject(new String(Base64.getDecoder().decode(this.token.substring(0, this.token.indexOf('.')))));
        this.userId = payload.getString("user_id");
        this.displayName = payload.getString("email");
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