package com.spyder.app.activitys.presenter;


import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.response.BaseContext;

public interface SpyderContract {
    interface View {
        void successResponse(BaseContext baseContext);
        void failureResponse(String error);
    }

    interface Presenter {
        void saveUserDetails(UserDetails details);
    }

}
