package com.boala.fixcar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FixCarClient2 {
    private static final String BASE_URL = "https://fixcarcesur.herokuapp.com/api/";
    private static FixCarClient2 mInstance;
    private Retrofit retrofit;

    private FixCarClient2() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new
                OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder().header("Authorization"
                        , Credentials.basic("Cesur", "FixCar"));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).addInterceptor(loggingInterceptor).build();
        Gson gson = new GsonBuilder().setLenient().serializeNulls().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    public static synchronized FixCarClient2 getInstance() {
        if (mInstance == null) {
            mInstance = new FixCarClient2();
        }
        return mInstance;
    }

    public FixCarApi getApi() {
        return retrofit.create(FixCarApi.class);
    }
}
