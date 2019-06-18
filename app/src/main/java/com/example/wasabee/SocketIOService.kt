package com.example.wasabee

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.emitter.Emitter
import com.google.gson.JsonObject
import org.json.JSONObject
import androidx.localbroadcastmanager.content.LocalBroadcastManager




class SocketIOService : Service() {
    private var io: Socket? = null
    var mBinder: IBinder = LocalBinder()

    override fun onCreate() {
        try {
            val preferenceFile = applicationContext.getString(R.string.preference_file_key)
            val token = getSharedPreferences(preferenceFile, 0).getString("token", null)
            val options = IO.Options()
            options.query = "authToken=$token"
            this.io = IO.socket("http://192.168.43.128:8080", options)
            this.io!!.on("message", onNewMessage);
            this.io!!.connect()

        } catch (e: Exception) {
            throw e
        }

        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    inner class LocalBinder : Binder() {
        val serverInstance: SocketIOService
            get() = this@SocketIOService
    }

    private val onNewMessage = Emitter.Listener { args ->
        Log.d("newMessage", args.toString())

        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
        if (getSharedPreferences(preferenceFile, 0).getBoolean("isInMessageListActivity", false)) {
            Log.d("if", "abc")
            val intent = Intent("updates")
            intent.putExtra("updates", args[0].toString())
            sendBroadcast(intent);
        } else {
            Log.d("else", "abc")
        }
        Log.d("updates", args.toString())
    }

    fun sendMessage(message: JsonObject) {
        try {
            io!!.emit("newMessage", message);
        } catch (e: Exception) {
            Log.d("socketio", e.toString())
        }
    }
}