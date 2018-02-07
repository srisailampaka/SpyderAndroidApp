package com.spyder.app.activitys.webservices;


import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.response.BaseContext;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Srisailam Paka on 13-07-2017.
 */

public interface ServiceProxy {
    String USER_DETAILS = "SaveUserDetails";


    @POST(USER_DETAILS)
    Call<BaseContext> saveUserDetails(@Body UserDetails details);

}
