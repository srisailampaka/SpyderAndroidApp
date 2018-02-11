package com.spyder.app.activitys.response;

/**
 * Created by srisailampaka on 11/02/18.
 */

public class UserDetailsResponse extends BaseContext {
    private String username;
    private String deviceid;
    private String userId;

    /**
     * No args constructor for use in serialization
     */
    public UserDetailsResponse() {
    }

    /**
     * @param username
     * @param userId
     * @param deviceid
     */
    public UserDetailsResponse(String username, String deviceid, String userId) {
        super();
        this.username = username;
        this.deviceid = deviceid;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
