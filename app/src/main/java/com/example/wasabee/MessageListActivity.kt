package com.example.wasabee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wasabee.data.model.Message
import com.example.wasabee.data.model.User

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

        viewManager = LinearLayoutManager(this)
        viewAdapter = MessageListAdapter(messages, this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_message_list).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    // Adds animals to the empty animals ArrayList
    fun addMessages() {
        val user = User("1", "2", "3")
        val message = Message("4", user, "5")
        messages.add(message)
    }
}