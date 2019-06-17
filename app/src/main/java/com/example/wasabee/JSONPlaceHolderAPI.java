package com.example.wasabee;

import com.example.wasabee.data.model.Token;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JSONPlaceHolderAPI {
    @POST("/login")
    Call<Token> login(@Body JsonObject loginInfo);



}
