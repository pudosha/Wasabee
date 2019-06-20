package com.example.wasabee

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wasabee.data.model.Message
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.util.*


class SocketIOService : Service() {
    private var io: Socket? = null
    var mBinder: IBinder = LocalBinder()

    override fun onCreate() {
        try {
            val preferenceFile = applicationContext.getString(R.string.preference_file_key)
            val token = getSharedPreferences(preferenceFile, 0).getString("token", null)
            val options = IO.Options()
            options.query = "authToken=$token"
            options.forceNew = true;
            Log.d("abc", "a")
            this.io = IO.socket("http://52.15.191.177", options)
            this.io!!.on("newMessage", onNewMessage);
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
            val message = Gson().fromJson(args[0].toString(), Message::class.java)
            val resultIntent = Intent(this, MessageListActivity::class.java)
            resultIntent.putExtra("chatID", message.chatID)
            val resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            var builder = NotificationCompat.Builder(this, "M_CH_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(message.username)
                .setContentText(message.message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(this)) {
                notify(message.messageID.toInt(), builder.build())
            }
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

    /* This lil boi right here is not guaranteed to be called)9 Guess it's time for kostyl
    override fun onDestroy() {
        Log.d("onDestroy", "destroyed")
        io!!.off();
        io!!.disconnect()
        super.onDestroy()
    }
    */
}