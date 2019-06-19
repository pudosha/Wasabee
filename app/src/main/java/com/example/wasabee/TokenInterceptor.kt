package com.example.wasabee

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        if (original.url().encodedPath().contains("/login") && original.method().equals("post")
            || (original.url().encodedPath().contains("/signUp") && original.method().equals("post"))
        ) {
            return chain.proceed(original)
        }

        val originalHttpUrl = original.url()
        //val preferenceFile = applicationContext.getString(R.string.preference_file_key)
        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", "token")
            .url(originalHttpUrl)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}