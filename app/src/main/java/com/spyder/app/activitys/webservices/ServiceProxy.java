package com.spyder.app.activitys.webservices;


import com.spyder.app.activitys.request.CallHistoryDetails;
import com.spyder.app.activitys.request.LocationDetails;
import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.request.UserId;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.response.GetCallHistoryResponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Srisailam Paka on 13-07-2017.
 */

public interface ServiceProxy {
    String USER_DETAILS = "SaveUserDetails";
    String Save_Location_Details="SaveLocationDetails";
    String Save_call_History_Details="SaveCallHistoryDetails";
    String GetCall_History_Details="GetCallHistoryDetails";


    @POST(USER_DETAILS)
    Call<BaseContext>saveUserDetails(@Body UserDetails details);
    @POST(Save_Location_Details)
    Call<BaseContext>saveLocationDetails(@Body LocationDetails locationDetails);

    @POST(Save_call_History_Details)
    Call<BaseContext>savecallHistoryDetails(@Body CallHistoryDetails callHistoryDetails);
    @POST(GetCall_History_Details)
    Call<GetCallHistoryResponce>getCallHistoryDetails(@Body UserId userId);
}
