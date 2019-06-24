package spb.summer_practice.wasabee;

import spb.summer_practice.wasabee.data.model.Chat;
import spb.summer_practice.wasabee.data.model.ChatPreview;
import spb.summer_practice.wasabee.data.model.Message;
import spb.summer_practice.wasabee.data.model.UserData;
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

    @POST("/createChat")
    Call<Chat> createChat(@Body JsonObject newChatInfo);

    @POST("/deleteChat")
    Call<Chat> deleteChat(@Body JsonObject chatInfo);

    @GET("/getMessages")
    Call<ArrayList<Message>> getMessages(
            @Query("chatID") String chatID,
            @Query("lastMessageID") String lastMessageID,
            @Query("messageCount") Integer messageCount
    );

    @POST("/editMessage")
    Call<Chat> editMessage(@Body JsonObject editedMessageInfo);

    @POST("/deleteMessage")
    Call<Chat> deleteMessage(@Body JsonObject messageInfo);


}
