package com.example.wasabee;

import com.example.wasabee.data.model.UserData;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JSONPlaceHolderAPI {
    @POST("/login")
    Call<UserData> login(@Body JsonObject loginInfo);

    @POST("/signUp")
    Call<UserData> signUp(@Body JsonObject signUpInfo);

}
