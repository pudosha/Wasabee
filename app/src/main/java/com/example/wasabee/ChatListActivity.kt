package com.example.wasabee

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wasabee.data.model.ChatPreview
import kotlinx.android.synthetic.main.activity_chat_info.*
import kotlinx.android.synthetic.main.activity_chat_list.*
import retrofit2.Call
import retrofit2.Response
import java.util.*


class ChatListActivity : AppCompatActivity(), ChatListAdapter.OnChatListener {

    // Initializing an empty ArrayList to be filled with animals
    lateinit var chats: ArrayList<ChatPreview>

    private lateinit var mAdapter: ChatListAdapter
    private var date = Calendar.getInstance()
    private var mBounded = false
    private var mServer: SocketIOService? = null

    private lateinit var API: JSONPlaceHolderAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        API = NetworkService.getInstance(this).jsonApi
        API.getChatList()
            .enqueue(object : retrofit2.Callback<ArrayList<ChatPreview>> {
                override fun onResponse(
                    call: Call<ArrayList<ChatPreview>>,
                    response: Response<ArrayList<ChatPreview>>
                ) {
                    chats = response.body()!!
                    mAdapter = ChatListAdapter(chats, this@ChatListActivity)
                    recyclerview_chat_list.layoutManager = LinearLayoutManager(this@ChatListActivity)
                    recyclerview_chat_list.adapter = mAdapter

                }

                override fun onFailure(call: Call<ArrayList<ChatPreview>>, t: Throwable) {
                    Toast.makeText(
                        this@ChatListActivity,
                        "Error occurred while getting server request. Please check your connection and try again",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })


        /*
        chats.add(Chat("GoOd BoIs", "So we goin' out for pizzas tonight?", "11:42", "Brendon Urie"))
        chats.add(Chat("Wiener dogs fan club", "Give me that upgrade. Upgrade. Ipgrade. Gimme that upgraaaaaaaaaade", "18:10", "Jeremy"))
        chats.add(Chat("Podgotovochka", "JJJJJJJ fezeka", "22:19", "Alexander Ognёv"))
        */

        newChatButton.setOnClickListener {
            startActivity(Intent(this, NewChatPeopleActivity::class.java))
        }
        /*class ViewHolder : RecyclerView.ViewHolder, View.OnCreateContextMenuListener {

            public ViewHolder(View v)
            {
                super(v);
                val chatList: RecyclerView = findViewById(R.id.recyclerview_chat_list) as RecyclerView
                registerForContextMenu(chatList)
                v.setOnCreateContextMenuListener(this);
            }
            //val chatList: RecyclerView = findViewById(R.id.recyclerview_chat_list) as RecyclerView
            registerForContextMenu(chatList)
            val editButt: Button = findViewById(R.id.editButton) as Button
            registerForContextMenu(editButt)
        }*/

        val chatList: RecyclerView = findViewById(R.id.recyclerview_chat_list) as RecyclerView
        registerForContextMenu(chatList)
    }


        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.setHeaderTitle("Context Menu");
            menu.add(0, v.getId(), 0, "Upload");
            menu.add(0, v.getId(), 0, "Search");
        }

        override fun onContextItemSelected(item: MenuItem): Boolean {
            Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show()
            return true
        }

        override fun onChatClick(position: Int) {
            val goToMessagesIntent = Intent(this, MessageListActivity::class.java)
            goToMessagesIntent.putExtra("chatID", chats.get(position).chat.chatID)
            startActivity(goToMessagesIntent)
        }

        override fun onBackPressed() {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }