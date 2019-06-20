package com.example.wasabee;

import com.example.wasabee.data.model.Chat;
import com.example.wasabee.data.model.ChatPreview;
import com.example.wasabee.data.model.UserData;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.ArrayList;

public interface JSONPlaceHolderAPI {
    @POST("/login")
    Call<UserData> login(@Body JsonObject loginInfo);

    @POST("/signUp")
    Call<UserData> signUp(@Body JsonObject signUpInfo);

    @GET("/getChatList")
    Call<ArrayList<ChatPreview>> getChatList();

    @POST("/createChat")
    Call<Chat> createChat(@Body JsonObject newChatInfo);


}
