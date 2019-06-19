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

        val people = ArrayList<String>()
        people.add("Jim")
        people.add("Becky")
        people.add("Connie")
        people.add("Jim")
        people.add("Becky")
        people.add("Connie")
        people.add("Jim")
        people.add("Becky")
        people.add("Connie")
        people.add("Jim")
        people.add("Becky")
        people.add("Connie")
        people.add("Jim")
        people.add("Becky")
        people.add("Connie")
        people.add("Jim")
        people.add("Becky")
        people.add("Connie")

        val peopleAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, people)

        peopleList.adapter = peopleAdapter
        peopleList.setOnItemClickListener {parent, view, position, id ->
            people.removeAt(position)
            peopleAdapter.notifyDataSetChanged()
        }

        goToNext.setOnClickListener {
            startActivity(Intent(this, CreateChatActivity::class.java))
        }
    }


}
