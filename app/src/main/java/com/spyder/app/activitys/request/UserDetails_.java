package com.spyder.app.activitys.request;

/**
 * Created by VenkatPc on 2/6/2018.
 */

public class UserDetails_ {
    private String username;
    private String deviceid;


    /**
     * No args constructor for use in serialization
     */
    public UserDetails_() {
    }

    /**
     * @param username
     * @param deviceid
     */
    public UserDetails_(String username, String deviceid) {
        super();
        this.username = username;
        this.deviceid = deviceid;

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



}
