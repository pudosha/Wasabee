package com.example.wasabee;

import com.example.wasabee.data.model.ChatPreview;
import com.example.wasabee.data.model.Message;
import com.example.wasabee.data.model.UserData;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.ArrayList;

public interface JSONPlaceHolderAPI {
    @POST("/login")
    Call<UserData> login(@Body JsonObject loginInfo);

    @POST("/signUp")
    Call<UserData> signUp(@Body JsonObject signUpInfo);

    @GET("/getChatList")
    Call<ArrayList<ChatPreview>> getChatList();

    @GET("/getMessages")
    Call<ArrayList<Message>> getMessages(
            @Query("chatID") String chatID,
            @Query("lastMessageID") String lastMessageID,
            @Query("messageCount") Integer messageCount
    );

}
