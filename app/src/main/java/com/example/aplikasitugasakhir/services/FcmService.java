package com.example.aplikasitugasakhir.services;

import com.example.aplikasitugasakhir.model.MessageFcm;
import com.example.aplikasitugasakhir.model.MessageFcmResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FcmService {

    @POST("send")
    Call<MessageFcmResponse> sendMessage(@Body MessageFcm messageFcm);

}
