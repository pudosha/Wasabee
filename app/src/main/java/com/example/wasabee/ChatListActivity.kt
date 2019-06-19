package com.example.wasabee

import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasabee.data.model.Message
import kotlinx.android.synthetic.main.activity_message_list.*
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import com.example.wasabee.SocketIOService.LocalBinder
import android.widget.Toast
import android.os.IBinder
import android.content.ComponentName
import android.content.ServiceConnection
import android.util.Log
//import com.example.wasabee.ChatListAdapter.OnChatListener
import com.example.wasabee.data.model.Chat
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_chat_list.*


class ChatListActivity : AppCompatActivity(), ChatListAdapter.OnChatListener {

    // Initializing an empty ArrayList to be filled with animals
    val chats: ArrayList<Chat> = ArrayList()

    private var mAdapter = ChatListAdapter(chats, this)
    private var date = Calendar.getInstance()
    private var mBounded = false
    private var mServer: SocketIOService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        chats.add(Chat("GoOd BoIs", "So we goin' out for pizzas tonight?", "11:42", "Brendon Urie"))
        chats.add(Chat("Wiener dogs fan club", "Give me that upgrade", "18:10", "Jeremy"))
        chats.add(Chat("Podgotovochka", "JJJJJJJ fezeka", "22:19", "Alexander Ognёv"))

        recyclerview_chat_list.layoutManager = LinearLayoutManager(this)
        recyclerview_chat_list.adapter = mAdapter

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