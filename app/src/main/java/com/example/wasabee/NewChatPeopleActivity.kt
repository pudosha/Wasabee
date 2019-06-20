package com.example.wasabee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasabee.data.model.Chat
import com.example.wasabee.data.model.ChatPreview
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_new_chat_people.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat_list.*
import retrofit2.Call
import retrofit2.Response


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
            val username = usernameInput.text.toString()
            if (username.length != 0) {
                people.add(username)
                peopleAdapter.notifyDataSetChanged()
                usernameInput.text.clear()
            }
        }
        peopleList.setOnItemClickListener { parent, view, position, id ->
            people.removeAt(position)
            peopleAdapter.notifyDataSetChanged()
        }

        createChatNewPeopleActivity.setOnClickListener {
            val newChatInfo = JsonObject()
            newChatInfo.addProperty("chatName", chatNameInput.text.toString())
            val usernamesArray = Gson().toJsonTree(people).asJsonArray
            newChatInfo.add("usernames", usernamesArray)

            val API = NetworkService.getInstance(this).jsonApi
            API.createChat(newChatInfo)
                .enqueue(object : retrofit2.Callback<Chat> {
                    override fun onResponse(
                        call: Call<Chat>,
                        response: Response<Chat>
                    ) {
                        val chat = response.body()!!
                        val goToMessagesIntent = Intent(this@NewChatPeopleActivity, MessageListActivity::class.java)
                        goToMessagesIntent.putExtra("chatID", chat.chatID)
                        goToMessagesIntent.putExtra("chatName", chat.chatName)
                        startActivity(goToMessagesIntent)
                    }

                    override fun onFailure(call: Call<Chat>, t: Throwable) {
                        Toast.makeText(
                            this@NewChatPeopleActivity,
                            "Error occurred while getting server request. Please check your connection and try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
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
