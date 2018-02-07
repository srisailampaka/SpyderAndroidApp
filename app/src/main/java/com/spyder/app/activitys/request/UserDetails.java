package com.spyder.app.activitys.request;

public class UserDetails {

    private UserDetails_ userDetails;

    public UserDetails() {
    }

    /**
     * @param userDetails
     */
    public UserDetails(UserDetails_ userDetails) {
        super();
        this.userDetails = userDetails;
    }

    public UserDetails_ getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails_ userDetails) {
        this.userDetails = userDetails;
    }


}
