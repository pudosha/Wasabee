package com.example.wasabee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wasabee.data.model.Message
import com.example.wasabee.data.model.User
import kotlinx.android.synthetic.main.activity_message_list.*

class MessageListActivity : AppCompatActivity() {

    // Initializing an empty ArrayList to be filled with animals
    val messages: ArrayList<Message> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        addMessages()

        recyclerview_message_list.layoutManager = LinearLayoutManager(this)

        val mAdapter = MessageListAdapter(messages, this)
        recyclerview_message_list.adapter = mAdapter

        val user = User("1", "Sir", "3")
        val message = Message("hey!", user, "11:05")
        messages.add(message)

    }

    // Adds animals to the empty animals ArrayList
    fun addMessages() {
        val user = User("1", "exampleUser1", "3")
        val message = Message("test message", user, "11:04")
        messages.add(message)
    }
}