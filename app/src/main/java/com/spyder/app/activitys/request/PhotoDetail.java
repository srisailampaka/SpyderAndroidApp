package com.spyder.app.activitys.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoDetail {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("lattitude")
    @Expose
    private String lattitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * No args constructor for use in serialization
     *
     */
    public PhotoDetail() {
    }

    /**
     *
     * @param timestamp
     * @param userId
     * @param lattitude
     * @param image
     * @param longitude
     */
    public PhotoDetail(String userId, String timestamp, String lattitude, String longitude, String image) {
        super();
        this.userId = userId;
        this.timestamp = timestamp;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.image = image;
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

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}