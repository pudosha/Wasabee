package com.example.wasabee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_chat_list.*
import kotlinx.android.synthetic.main.activity_new_chat_people.*

class NewChatPeopleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat_people)

        goToNext.setOnClickListener {
            startActivity(Intent(this, CreateChatActivity::class.java))
        }

        val people = Array(10) {i -> "Person $i"}
        val peopleAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, people)
        peopleList.adapter = peopleAdapter
        peopleList.setOnItemClickListener {parent, view, position, id ->
            (view as TextView).text = (view as TextView).text.toString() + ". checked"
            //TODO: implement checkboxes to add and un-add people to new chat

        }

    }


}
