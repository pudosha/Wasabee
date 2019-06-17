package com.example.wasabee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wasabee.data.model.Token
import com.google.gson.JsonObject
import com.squareup.okhttp.Callback
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var API: JSONPlaceHolderAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        API = NetworkService.getInstance().getJSONApi()
        loginButton.setOnClickListener {
            val loginInfo = JsonObject()
            loginInfo.addProperty("username", edittext_username.text.toString())
            loginInfo.addProperty("password", edittext_password.text.toString())
            API.login(loginInfo)
                .enqueue(object: retrofit2.Callback<Token> {
                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        if (response.body() == null) {
                            Toast.makeText(this@LoginActivity, "Duck", Toast.LENGTH_LONG).show()
                            return
                        }
                        val res = response.body()!!

                        Toast.makeText(this@LoginActivity, res.message, Toast.LENGTH_LONG).show()
                        if (res.success) {
                            startActivity(Intent(this@LoginActivity, MainMenuActivity::class.java))
                            TODO("Save token")
                        } else {
                            edittext_password.text.clear()
                        }
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Toast.makeText(this@LoginActivity,
                            "Error occurred while getting request!", Toast.LENGTH_LONG).show()
                    }
                })
        }

    }
}
