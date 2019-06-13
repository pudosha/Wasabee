package com.example.wasabee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_starting_up.*

class StartingUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_up)

        buttonLogIn.setOnClickListener {
            val myCoolIntent = Intent(this, LoginActivity::class.java)
            startActivity(myCoolIntent)
        }

    }

}
