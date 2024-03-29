package spb.summer_practice.wasabee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import spb.summer_practice.wasabee.R
import kotlinx.android.synthetic.main.activity_starting_up.*

class StartingUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
        val token = getSharedPreferences(preferenceFile, 0).getString("token", null)
        if(token !== null) {
            startActivity(Intent(this, MainMenuActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_starting_up)

        loginButton_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        /*
        goToMainMenuButton.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }

        goToMessagesButton.setOnClickListener {
            startActivity(Intent(this, MessageListActivity::class.java))
        }
        */

    }

    override fun onBackPressed() {
        val exit = Intent(Intent.ACTION_MAIN)
        exit.addCategory(Intent.CATEGORY_HOME)
        exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(exit)
    }

}
