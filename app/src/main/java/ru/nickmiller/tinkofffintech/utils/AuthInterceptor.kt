package ru.nickmiller.tinkofffintech.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.Response
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.ui.login.LoginActivity


class AuthInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.code() == 403) {
            Toast.makeText(context, context.getString(R.string.error_auth), Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
        return originalResponse
    }

}