package com.example.wasabee

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(var context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        if (original.url().encodedPath().contains("/login") && original.method().equals("POST")
            || (original.url().encodedPath().contains("/signUp") && original.method().equals("POST"))
        ) {
            return chain.proceed(original)
        }

        val originalHttpUrl = original.url()
        val preferenceFile = context.getString(R.string.preference_file_key)
        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", context.getSharedPreferences(preferenceFile, 0).getString("token", null))
            .url(originalHttpUrl)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}