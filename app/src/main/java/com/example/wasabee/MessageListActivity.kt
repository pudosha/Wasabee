package com.example.wasabee

import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasabee.data.model.Message
import kotlinx.android.synthetic.main.activity_message_list.*
import java.util.*
import kotlin.collections.ArrayList
import com.example.wasabee.SocketIOService.LocalBinder
import android.widget.Toast
import android.os.IBinder
import android.util.Log
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

        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
        with(getSharedPreferences(preferenceFile, 0).edit()) {
            putBoolean("isInMessageListActivity", true)
            apply()
        }

        val mIntent = Intent(this, SocketIOService::class.java)
        bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()

        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
        with(getSharedPreferences(preferenceFile, 0).edit()) {
            putBoolean("isInMessageListActivity", false)
            apply()
        }
    }

    fun br() = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val update = intent.getStringExtra("updates")
            Log.d("newUpdate", update)
        }
    }.also {receiver ->
        val intFilt = IntentFilter("updates");
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(receiver, intFilt);

    }


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