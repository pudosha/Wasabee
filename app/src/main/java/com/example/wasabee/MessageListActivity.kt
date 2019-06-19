package com.example.wasabee

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasabee.SocketIOService.LocalBinder
import com.example.wasabee.data.model.Message
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_message_list.*
import java.util.*
import kotlin.collections.ArrayList


class MessageListActivity : AppCompatActivity() {

    // Initializing an empty ArrayList to be filled with animals
    val messages: ArrayList<Message> = ArrayList()

    private var mAdapter = MessageListAdapter(messages, this)
    private var date = Calendar.getInstance()
    private var mBounded = false
    private var mServer: SocketIOService? = null
    private lateinit var bReciever: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        recyclerview_message_list.layoutManager = LinearLayoutManager(this)
        recyclerview_message_list.adapter = mAdapter

        button_chatbox_send.setOnClickListener {
            val message = JsonObject()
            // TODO("Actual chat id")
            message.addProperty("chatID", "123")
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

        chatInfo.setOnClickListener {
            startActivity(Intent(this, ChatInfoActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        bReciever = br();
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

        unregisterReceiver(bReciever)
        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
        with(getSharedPreferences(preferenceFile, 0).edit()) {
            putBoolean("isInMessageListActivity", false)
            apply()
        }
    }

    fun br() = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            //val update = JsonParser().parse(intent.getStringExtra("updates")!!).asJsonObject
            //val message = Message(update["message"].asString, update["date"].asString, update["sender"].asString)
            val message = Gson().fromJson(intent.getStringExtra("updates")!!, Message::class.java)
            Log.d("dbg", message.toString())
            messages.add(message)
            mAdapter.notifyItemInserted(messages.size - 1)
            mAdapter.notifyDataSetChanged()

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

    override fun onBackPressed() {
        startActivity(Intent(this, ChatListActivity::class.java))
    }

}