package com.example.brainymerchandising.Utils.Token


import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()



        // If token has been saved, add it to the request
        sessionManager.fetchRefreshToken()?.let {
            requestBuilder.addHeader("Authorization", "bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}
