package spb.summer_practice.wasabee

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import spb.summer_practice.wasabee.SocketIOService.LocalBinder
import spb.summer_practice.wasabee.data.model.Message
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_message_list.*
import retrofit2.Call
import retrofit2.Response
import java.util.*


class MessageListActivity : AppCompatActivity() {

    val messages: LinkedList<Message> = LinkedList()

    private var mAdapter = MessageListAdapter(messages, this)
    private var date = Calendar.getInstance()
    private var mBounded = false
    private var mServer: SocketIOService? = null
    private lateinit var bReciever: BroadcastReceiver
    private var chatID: String? = null
    private lateinit var API: JSONPlaceHolderAPI
    private var isStartReached = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        API = NetworkService.getInstance(this).jsonApi
        chatID = getIntent().getStringExtra("chatID")!!

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        //fun setStackFromEnd(stackFromEnd: Boolean): Unit
        //val layoutManager = LinearLayoutManager(this)
        recyclerview_message_list.layoutManager = layoutManager
        recyclerview_message_list.adapter = mAdapter
        addMessages()

        recyclerview_message_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (recyclerView.computeVerticalScrollOffset() < 1000) {
                    //if (!recyclerView.canScrollVertically(-1) && !isStartReached) {
                    addMessages()
                }
            }
        })


        button_chatbox_send.setOnClickListener {
            if (edittext_chatbox.text.toString().length > 0) {
                val message = JsonObject()
                message.addProperty("chatID", chatID)
                message.addProperty("message", edittext_chatbox.text.toString())
                if (mBounded) {
                    mServer!!.sendMessage(message)
                    edittext_chatbox.text.clear()
                } else {
                    Toast.makeText(this@MessageListActivity, "Error sending message", Toast.LENGTH_LONG).show()
                }
            }

        }

        chatInfo.setOnClickListener {
            startActivity(Intent(this, ChatInfoActivity::class.java))
        }

        val messageList: RecyclerView = findViewById(R.id.recyclerview_message_list) as RecyclerView
        registerForContextMenu(messageList)
    }

    /*
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.message_sent_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editMessage -> {
                Toast.makeText(this, "This feature is a work in progress", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.forwardMessage -> {
                Toast.makeText(this, "Yup, still a work in progress", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.deleteMessage -> {
                Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }*/

    override fun onStart() {
        super.onStart()

        bReciever = br();
        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
        with(getSharedPreferences(preferenceFile, 0).edit()) {
            putString("messageListActivityChatID", chatID)
            apply()
        }

        val mIntent = Intent(this, SocketIOService::class.java)
        bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()

        isStartReached = false
        unregisterReceiver(bReciever)
        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
        with(getSharedPreferences(preferenceFile, 0).edit()) {
            putString("messageListActivityChatID", null)
            apply()
        }
    }

    var canGetMessages = true // костыль
    private fun addMessages() {
        if (!canGetMessages)
            return
        canGetMessages = false
        val firstMessageID: String?
        if (messages.size > 0) {
            firstMessageID = messages.last.messageID
            Log.d("messageID", firstMessageID)
        } else {
            firstMessageID = null
            Log.d("messageID", "null")
        }

        API.getMessages(chatID, firstMessageID, 50)
            .enqueue(object : retrofit2.Callback<ArrayList<Message>> {
                override fun onResponse(
                    call: Call<ArrayList<Message>>,
                    response: Response<ArrayList<Message>>
                ) {
                    Log.d("okok", "solution is correct")
                    val newMessages = response.body()!!
                    newMessages.reverse()
                    if (newMessages.size == 0)
                        isStartReached = true

                    newMessages.forEach { message ->
                        messages.add(message)
                    }

                    mAdapter.notifyItemInserted(messages.size - 1)
                    mAdapter.notifyDataSetChanged()
                    canGetMessages = true
                }

                override fun onFailure(call: Call<ArrayList<Message>>, t: Throwable) {
                    Log.d("i'm not ok", "and not sorry 4 that")
                    Toast.makeText(
                        this@MessageListActivity,
                        "Error occurred while getting server request. Please check your connection and try again",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })

    }


    fun br() = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            //val update = JsonParser().parse(intent.getStringExtra("updates")!!).asJsonObject
            //val message = Message(update["message"].asString, update["date"].asString, update["sender"].asString)
            val message = Gson().fromJson(intent.getStringExtra("updates")!!, Message::class.java)
            Log.d("dbg", message.toString())
            messages.addFirst(message)
            mAdapter.notifyItemInserted(messages.size - 1)
            mAdapter.notifyDataSetChanged()

        }
    }.also { receiver ->
        val intFilt = IntentFilter("updates");
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