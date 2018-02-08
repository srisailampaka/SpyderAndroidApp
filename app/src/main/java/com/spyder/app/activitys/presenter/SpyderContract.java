package com.spyder.app.activitys.presenter;


import com.spyder.app.activitys.request.CallHistoryDetails;
import com.spyder.app.activitys.request.LocationDetails;
import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.request.UserId;
import com.spyder.app.activitys.response.BaseContext;

import java.util.List;

public interface SpyderContract {
    interface View {
        void successResponse(BaseContext baseContext);
        void failureResponse(String error);
    }

    interface Presenter {
        void saveUserDetails(UserDetails details);

        void saveLocationDetails(LocationDetails locationDetails);
        void savecallHistoryDetails(CallHistoryDetails callHistoryDetails);
        void getCallHistoryDetails(UserId userId);
    }

}
