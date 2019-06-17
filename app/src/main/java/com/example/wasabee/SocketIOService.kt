package com.example.wasabee

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.emitter.Emitter
import org.json.JSONObject


class SocketIOService : Service() {
    private var io: Socket? = null
    var mBinder: IBinder = LocalBinder()

    override fun onCreate() {
        try {
            this.io = IO.socket("http://192.168.0.135:8080")
            this.io!!.on("Message", onNewMessage);
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
        val data = args[0] as JSONObject
        Log.d("newMessage", data.toString())
    }

    fun sendMessage(message: String) {
        try {
            io!!.emit("message", message);
        } catch (e: Exception) {
            Log.d("socketio", e.toString())
        }
    }
}