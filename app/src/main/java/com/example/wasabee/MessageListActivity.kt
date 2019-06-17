package com.example.wasabee

import android.content.Context
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
import com.google.gson.JsonObject


class MessageListActivity : AppCompatActivity() {

    // Initializing an empty ArrayList to be filled with animals
    val messages: ArrayList<Message> = ArrayList()

    private var mAdapter = MessageListAdapter(messages, this)
    private var date = Calendar.getInstance()
    private var mBounded = false
    private var mServer: SocketIOService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        recyclerview_message_list.layoutManager = LinearLayoutManager(this)
        recyclerview_message_list.adapter = mAdapter

        button_chatbox_send.setOnClickListener {
            val message = JsonObject()
            // TODO("Actual chat id")
            message.addProperty("chatId", "123")
            message.addProperty("message", edittext_chatbox.text.toString())
            if (mBounded) {
                mServer!!.sendMessage(message)
                edittext_chatbox.text.clear()
            } else {
                Toast.makeText(this@MessageListActivity, "Error sending message", Toast.LENGTH_LONG).show()
            }

            /*
            messages.add(message)
            mAdapter.notifyItemInserted(messages.size - 1)
            mAdapter.notifyDataSetChanged()
            */

        }
    }

    override fun onStart() {
        super.onStart()

        val mIntent = Intent(this, SocketIOService::class.java)
        bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE)
    };


    var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mBounded = false
            mServer = null
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBounded = true
            val mLocalBinder = service as LocalBinder
            mServer = mLocalBinder.serverInstance
        }
    }

}