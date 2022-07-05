package com.example.aplikasitugasakhir;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit;
    public static final String BASE_URL = "https://fcm.googleapis.com/fcm/";

    public static Retrofit getInstance() {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Authorization","key=AAAAJT_EbzM:APA91bEr9Gb8xX4eqJwJAczeWW-lYn9IVuDSbKdJzdssPRJo7yCsoJR5JDCTX2kbp32u35WsQ5BF4Awq2CmJUo0yBsHm0cHz7mInHMPlFSwzl-VlsS7XWq4DOv2MEedmQ7PbrfAqbeZ3")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();



        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
