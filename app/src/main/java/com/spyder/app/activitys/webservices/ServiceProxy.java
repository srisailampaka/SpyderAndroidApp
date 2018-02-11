package com.spyder.app.activitys.webservices;


import com.spyder.app.activitys.request.CallHistoryDetails;
import com.spyder.app.activitys.request.LocationDetails;
import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.request.UserId;
import com.spyder.app.activitys.request.UserPhotoDetailList;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.response.GetCallHistoryResponce;
import com.spyder.app.activitys.response.UserDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Srisailam Paka on 13-07-2017.
 */

public interface ServiceProxy {
    String user_Details = "SaveUserDetails";
    String save_Location_Details="SaveLocationDetails";
    String save_call_History_Details="SaveCallHistoryDetails";
    String get_call_History_Details="GetCallHistoryDetails";
    String save_photo_Details="SavePhotoDetails";


    @POST(user_Details)
    Call<UserDetailsResponse>saveUserDetails(@Body UserDetails details);
    @POST(save_Location_Details)
    Call<BaseContext>saveLocationDetails(@Body LocationDetails locationDetails);

    @POST(save_call_History_Details)
    Call<BaseContext>savecallHistoryDetails(@Body CallHistoryDetails callHistoryDetails);
    @POST(get_call_History_Details)
    Call<GetCallHistoryResponce>getCallHistoryDetails(@Body UserId userId);
    @POST(save_photo_Details)
    Call<BaseContext>savePhotoDetails(@Body UserPhotoDetailList userPhotoDetailList);
}
