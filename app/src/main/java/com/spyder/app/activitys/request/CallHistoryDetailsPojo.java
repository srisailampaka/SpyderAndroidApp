package com.spyder.app.activitys.request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallHistoryDetailsPojo {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("typeOfCall")
    @Expose
    private String typeOfCall;
    @SerializedName("duration")
    @Expose
    private String duration;

    /**
     * No args constructor for use in serialization
     *
     */
    public CallHistoryDetailsPojo() {
    }

    /**
     *
     * @param timestamp
     * @param typeOfCall
     * @param duration
     * @param phoneNumber
     * @param userId
     */
    public CallHistoryDetailsPojo(String userId, String timestamp, String phoneNumber, String typeOfCall, String duration) {
        super();
        this.userId = userId;
        this.timestamp = timestamp;
        this.phoneNumber = phoneNumber;
        this.typeOfCall = typeOfCall;
        this.duration = duration;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTypeOfCall() {
        return typeOfCall;
    }

    public void setTypeOfCall(String typeOfCall) {
        this.typeOfCall = typeOfCall;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}




