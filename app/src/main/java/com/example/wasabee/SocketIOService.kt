package com.example.wasabee

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.emitter.Emitter
import org.json.JSONObject


class SocketIOService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private var io: Socket? = null

    override fun onCreate() {
        try {
            this.io = IO.socket("http://127.0.0.1")
            this.io!!.connect()
            this.io!!.on("new message", onNewMessage);

        } catch (e: Exception) {
            throw e
        }

        super.onCreate()
    }

    private val onNewMessage = Emitter.Listener { args ->
        val data = args[0] as JSONObject

        Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
    }

    fun onHandleIntent(message: Intent?) {
        try {
            io!!.emit(message!!.dataString);
        } catch (e: Exception) {

        }
    }
}