package com.example.wasabee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_chat_list.*
import kotlinx.android.synthetic.main.activity_starting_up.*

class ChatListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)


        val chats = Array(10) {i -> "Chat $i"}
        val chatsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chats)
        chatList.adapter = chatsAdapter
        chatList.setOnItemClickListener {parent, view, position, id ->
            //inputTxt.setText((view as TextView).text, TextView.BufferType.EDITABLE)
            (view as TextView).text = (view as TextView).text.toString() + ". read"
        }

        newChatButton.setOnClickListener {
            startActivity(Intent(this, NewChatPeopleActivity::class.java))
        }
    }
}
