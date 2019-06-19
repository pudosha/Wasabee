package com.example.wasabee

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wasabee.data.model.UserData
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var API: JSONPlaceHolderAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        API = NetworkService.getInstance().getJSONApi()
        signUpButton_sign_up.setOnClickListener {
            val signUpInfo = JsonObject()
            var username = edittext_username_sign_up.text.toString()
            var password = edittext_password_sign_up.text.toString()
            var repeatedPassword = edittext_repeat_password_sign_up.text.toString()

            if (username.length == 0 || password.length == 0) {
                Toast.makeText(
                    this@SignUpActivity,
                    "You can't have empty strings as login or password",
                    Toast.LENGTH_LONG
                ).show()
                // edittext_password_sign_up.text.clear()
                // edittext_repeat_password_sign_up.text.clear()
                return@setOnClickListener
            }

            val password_min_length: Int = 7
            if (password.length < password_min_length) {
                Toast.makeText(
                    this@SignUpActivity,
                    "The password has to be at least $password_min_length characters long",
                    Toast.LENGTH_LONG
                ).show()
                // edittext_password_sign_up.text.clear()
                // edittext_repeat_password_sign_up.text.clear()
                return@setOnClickListener
            }
            if (password != repeatedPassword) {
                Toast.makeText(this@SignUpActivity, "The passwords are different", Toast.LENGTH_LONG).show()
                // edittext_password_sign_up.text.clear()
                // edittext_repeat_password_sign_up.text.clear()
                return@setOnClickListener
            }

            signUpInfo.addProperty("username", username)
            signUpInfo.addProperty("password", password)

            API.signUp(signUpInfo)
                .enqueue(object : retrofit2.Callback<UserData> {
                    override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                        if (response.body() == null) {
                            Toast.makeText(this@SignUpActivity, "Username $username is already taken. Please choose another one", Toast.LENGTH_LONG).show()
                            return
                        }

                        val res = response.body()!!
                        Toast.makeText(this@SignUpActivity, "We're glad to have you with us, $username!", Toast.LENGTH_LONG).show()
                        val preferenceFile = applicationContext.getString(R.string.preference_file_key)
                        with(getSharedPreferences(preferenceFile, 0).edit()) {
                            putString("token", res.token)
                            putString("username", res.username)
                            apply()
                        }
                        startActivity(Intent(this@SignUpActivity, MainMenuActivity::class.java))
                        /*
                        else {
                            ыыы не хочу вводить пароль 10 раз
                            edittext_password_sign_up.text.clear()
                            edittext_repeat_password_sign_up.text.clear()

                        }
                        */
                    }

                    override fun onFailure(call: Call<UserData>, t: Throwable) {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Error occurred while getting server request. Please check your connection and try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

    }
}
