package com.spyder.app.activitys.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserId {

    @SerializedName("userId")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserId() {
    }

    /**
     *
     * @param userId
     */
    public UserId(String userId) {
        super();
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}