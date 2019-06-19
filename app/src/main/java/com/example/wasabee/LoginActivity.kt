package com.example.wasabee

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wasabee.data.model.UserData
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
                .enqueue(object : retrofit2.Callback<UserData> {
                    override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                        if (response.body() == null) {
                            Toast.makeText(this@LoginActivity, "duck.", Toast.LENGTH_LONG).show()
                            return
                        }
                        val res = response.body()!!

                        Toast.makeText(this@LoginActivity, "ok", Toast.LENGTH_LONG).show()
                        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
                        with(getSharedPreferences(preferenceFile, 0).edit()) {
                            putString("token", res.token)
                            putString("username", res.username)
                            apply()
                        }
                        startActivity(Intent(this@LoginActivity, MainMenuActivity::class.java))
                    }

                    override fun onFailure(call: Call<UserData>, t: Throwable) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error occurred while getting server request. Please check your connection and try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

    }
}
