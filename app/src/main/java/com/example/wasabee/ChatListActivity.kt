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

    val chats: ArrayList<Chat> = ArrayList()

    private var mAdapter = ChatListAdapter(chats, this)
    private var date = Calendar.getInstance()
    private var mBounded = false
    private var mServer: SocketIOService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        chats.add(Chat("GoOd BoIs","123", "So we goin' out for pizzas tonight?", "22:19", "Brendon Urie"))
        chats.add(Chat("Wiener dogs fan club", "2meh", "Give me that upgrade. Upgrade.\n Ipgrade. Gimme that upgraaaaaaaaaade", "18:10", "Jeremy"))
        chats.add(Chat("Podgotovochka", "3MEgan", "JJJJJJJ fezeka", "11:42", "Alexander Ognёv"))

        recyclerview_chat_list.layoutManager = LinearLayoutManager(this)
        recyclerview_chat_list.adapter = mAdapter

        newChatButton.setOnClickListener {
            startActivity(Intent(this, NewChatPeopleActivity::class.java))
        }
    }

    override fun onChatClick(position: Int) {
        val goToMessagesIntent = Intent(this, MessageListActivity::class.java)
        goToMessagesIntent.putExtra("chatID", chats.get(position).chatID)
        startActivity(goToMessagesIntent)
        //startActivity(Intent(this, MessageListActivity::class.java).putExtra("chatID", chats.get(position).chatID))
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainMenuActivity::class.java))
    }
}