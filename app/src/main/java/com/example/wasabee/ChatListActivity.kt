package com.example.wasabee

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasabee.data.model.ChatPreview
import kotlinx.android.synthetic.main.activity_chat_list.*
import retrofit2.Call
import retrofit2.Response
import java.util.*


class ChatListActivity : AppCompatActivity(), ChatListAdapter.OnChatListener {

    // Initializing an empty ArrayList to be filled with animals
    lateinit var chats: ArrayList<ChatPreview>

    private lateinit var mAdapter: ChatListAdapter
    private var date = Calendar.getInstance()
    private var mBounded = false
    private var mServer: SocketIOService? = null

    private lateinit var API: JSONPlaceHolderAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        API = NetworkService.getInstance(this).jsonApi
        API.getChatList()
            .enqueue(object : retrofit2.Callback<ArrayList<ChatPreview>> {
                override fun onResponse(
                    call: Call<ArrayList<ChatPreview>>,
                    response: Response<ArrayList<ChatPreview>>
                ) {
                    chats = response.body()!!
                    mAdapter = ChatListAdapter(chats, this@ChatListActivity)
                    recyclerview_chat_list.layoutManager = LinearLayoutManager(this@ChatListActivity)
                    recyclerview_chat_list.adapter = mAdapter

                }

                override fun onFailure(call: Call<ArrayList<ChatPreview>>, t: Throwable) {
                    Toast.makeText(
                        this@ChatListActivity,
                        "Error occurred while getting server request. Please check your connection and try again",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        /*
        chats.add(Chat("GoOd BoIs", "So we goin' out for pizzas tonight?", "11:42", "Brendon Urie"))
        chats.add(Chat("Wiener dogs fan club", "Give me that upgrade. Upgrade. Ipgrade. Gimme that upgraaaaaaaaaade", "18:10", "Jeremy"))
        chats.add(Chat("Podgotovochka", "JJJJJJJ fezeka", "22:19", "Alexander Ognёv"))
        */


        newChatButton.setOnClickListener {
            startActivity(Intent(this, NewChatPeopleActivity::class.java))
        }
    }

    override fun onChatClick(position: Int) {
        //chats.get(position)
        startActivity(Intent(this, MessageListActivity::class.java))
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainMenuActivity::class.java))
    }
}