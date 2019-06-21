package com.example.wasabee

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
            this.io = IO.socket("http://3.13.62.48", options)
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

        val message = Gson().fromJson(args[0].toString(), Message::class.java)

        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
        val currentChatID = getSharedPreferences(preferenceFile, 0).getString("messageListActivityChatID", null)
        val messageChatID = message.chatID
        if (currentChatID != null) {
            Log.d("curr", currentChatID)
        } else {
            Log.d("curr", "null")
        }
        Log.d("msg", messageChatID)
        if (currentChatID == messageChatID) {
            Log.d("if", "abc")
            val intent = Intent("updates")
            intent.putExtra("updates", args[0].toString())
            sendBroadcast(intent);
        } else {
            val resultIntent = Intent(this, MessageListActivity::class.java)
            resultIntent.putExtra("chatID", message.chatID)
            val resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val builder = NotificationCompat.Builder(this, "M_CH_ID")
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