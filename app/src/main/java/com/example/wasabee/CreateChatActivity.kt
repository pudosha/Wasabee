package com.example.wasabee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_chat_list.*

class CreateChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_chat)

        val chosenPeople = Array(10) {i -> "Person $i"}
        val chosenPeopleAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chosenPeople)
        chosenPeopleList.adapter = chosenPeopleAdapter
    }
}
