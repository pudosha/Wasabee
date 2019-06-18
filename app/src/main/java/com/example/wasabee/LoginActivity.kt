package com.example.wasabee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wasabee.data.model.Token
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var API: JSONPlaceHolderAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        API = NetworkService.getInstance().getJSONApi()
        loginButton_login.setOnClickListener {
            val loginInfo = JsonObject()
            loginInfo.addProperty("username", edittext_username_login.text.toString())
            loginInfo.addProperty("password", edittext_password_login.text.toString())
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
                            val preferenceFile = applicationContext.getString(R.string.preference_file_key)
                            with(getSharedPreferences(preferenceFile, 0).edit()) {
                                putString("token", res.token)
                                apply()
                            }
                            startActivity(Intent(this@LoginActivity, MainMenuActivity::class.java))
                        } else {
                            edittext_password_login.text.clear()
                        }
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error occurred while getting request!", Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

    }
}
