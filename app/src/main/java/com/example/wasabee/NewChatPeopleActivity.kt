package com.example.wasabee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_chat_people.*

class NewChatPeopleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat_people)

        goToNext.setOnClickListener {
            startActivity(Intent(this, CreateChatActivity::class.java))
        }

    }


}
