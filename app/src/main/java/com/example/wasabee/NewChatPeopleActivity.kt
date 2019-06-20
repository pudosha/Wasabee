package com.example.wasabee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_new_chat_people.*

class NewChatPeopleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat_people)

        val people = ArrayList<String>()
        /*people.add("Jim")
        people.add("Becky")
        people.add("Connie")*/

        val peopleAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, people)

        peopleList.adapter = peopleAdapter

        addUser.setOnClickListener {
            people.add(usernameInput.text.toString())
            peopleAdapter.notifyDataSetChanged()
            usernameInput.text.clear()
        }
        peopleList.setOnItemClickListener { parent, view, position, id ->
            people.removeAt(position)
            peopleAdapter.notifyDataSetChanged()
        }

        createChatNewPeopleActivity.setOnClickListener {
            val chatInfo = JsonObject()
            chatInfo.addProperty("chatName", chatNameInput.text.toString())
            //val usernamesArray = JsonArray(people)
            //chatInfo.addProperty("usernames", usernamesArray)

            val goToMessagesIntent = Intent(this, MessageListActivity::class.java)
            goToMessagesIntent.putExtra("chatID", "the chatID that Ser gives me")
            startActivity(goToMessagesIntent)
        }

        /*
        addCourseText = (EditText) findViewById (R.id.clEtAddCourse);
        addCourseText.setOnKeyListener(new OnKeyListener ()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch(keyCode)
                    {
                        case KeyEvent . KEYCODE_DPAD_CENTER :
                        case KeyEvent . KEYCODE_ENTER :
                        addCourseFromTextBox();
                        return true;
                        default:
                        break;
                    }
                }
                return false;
            }
        });
        */
    }
}
