package com.spyder.app.activitys.request;

import com.spyder.app.activitys.response.BaseContext;

public class UserDetails extends BaseContext{

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
