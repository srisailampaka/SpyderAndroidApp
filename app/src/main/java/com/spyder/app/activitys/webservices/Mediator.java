package com.spyder.app.activitys.webservices;

import android.content.Context;

import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.util.Constants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Srisailam paka on 14-07-2017.
 */

public class Mediator {

    private static final String TAG = Mediator.class.getSimpleName();
    private static Mediator mediator;
    private ServiceProxy mLoginServiceProxy;
    private String AUTH_PREFIX = "bearer ";
    private String token;
    private Context _context;

    public Mediator(final Context context) {
        this._context = context;

        Retrofit loginRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mLoginServiceProxy = loginRetrofit.create(ServiceProxy.class);
    }

    public static synchronized Mediator getInstance(Context context) {
        if (mediator == null) {
            mediator = new Mediator(context);
        }
        return mediator;
    }

    public Call<BaseContext> saveUserDetails(UserDetails details) {
        return mLoginServiceProxy.saveUserDetails(details);
    }

//    public void CreateProxyWithToken() {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.connectTimeout(3, TimeUnit.MINUTES)
//                .writeTimeout(3, TimeUnit.MINUTES)
//                .readTimeout(3, TimeUnit.MINUTES);
//
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                Request original = chain.request();
//                Request request = original.newBuilder()
//                        .header("Content-Type", "application/json")
//                        .method(original.method(), original.body())
//                        .build();
//
//                return chain.proceed(request);
//            }
//        });
////        Gson gson = new GsonBuilder()
////                .setDateFormat("MM/dd/YYYY")
////                .create();
//        OkHttpClient client = httpClient.build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//        mServiceProxy = retrofit.create(ServiceProxy.class);
//    }


}
