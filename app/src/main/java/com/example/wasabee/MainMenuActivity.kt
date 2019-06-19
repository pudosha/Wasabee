package com.example.wasabee

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.activity_starting_up.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, SocketIOService::class.java)
        startService(intent)

        setContentView(R.layout.activity_main_menu)

        chatsButton.setOnClickListener {
            startActivity(Intent(this, ChatListActivity::class.java))
        }

        logOutButton.setOnClickListener {
            val preferenceFile = applicationContext.getString(R.string.preference_file_key)
            getSharedPreferences(preferenceFile, 0).edit().remove("token").apply()
            startActivity(Intent(this, StartingUpActivity::class.java))
        }

        goToMessagesButton2.setOnClickListener {
            startActivity(Intent(this, MessageListActivity::class.java))
        }

    }

    override fun onBackPressed() {
        val exit = Intent(Intent.ACTION_MAIN)
        exit.addCategory(Intent.CATEGORY_HOME)
        exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(exit)
    }

}
