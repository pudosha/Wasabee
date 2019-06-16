package com.example.wasabee

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasabee.data.model.Message
import com.example.wasabee.data.model.User
import kotlinx.android.synthetic.main.activity_message_list.*
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import com.example.wasabee.SocketIOService.LocalBinder
import android.widget.Toast
import android.os.IBinder
import android.content.ComponentName
import android.content.ServiceConnection





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
            val user = User("1", "Sir", "3")
            val time = date.get(Calendar.HOUR_OF_DAY).toString() + ":" + date.get(Calendar.MINUTE).toString()
            val message = Message(edittext_chatbox.text.toString(), user, time)
            messages.add(message)
            mAdapter.notifyItemInserted(messages.size - 1)
            mAdapter.notifyDataSetChanged()

            edittext_chatbox.text.clear()

            if (mBounded) {
                mServer!!.sendMessage(message.toString())
            } else {
                Toast.makeText(this@MessageListActivity, "Service is connected", 1000).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val mIntent = Intent(this, SocketIOService::class.java)
        bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE)
    };


    // Adds animals to the empty animals ArrayList
    fun addMessages(newMessages: ArrayList<Message>) {
        messages.addAll(newMessages)
        mAdapter.notifyItemInserted(messages.size - 1)
        mAdapter.notifyDataSetChanged()
    }

    var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            //Toast.makeText(this@MessageListActivity, "Service is disconnected", 1000).show()
            mBounded = false
            mServer = null
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            //Toast.makeText(this@MessageListActivity, "Service is connected", 1000).show()
            mBounded = true
            val mLocalBinder = service as LocalBinder
            mServer = mLocalBinder.serverInstance
        }
    }

}