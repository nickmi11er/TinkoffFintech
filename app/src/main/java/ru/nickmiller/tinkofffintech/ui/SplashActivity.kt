package ru.nickmiller.tinkofffintech.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.nickmiller.tinkofffintech.ui.login.LoginActivity
import ru.nickmiller.tinkofffintech.utils.cookies.CookiesStore


class SplashActivity : AppCompatActivity() {
    val cookiesStore by inject<CookiesStore>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cookies = cookiesStore.getCookies()
        if ((cookies?.filter {
                it.contains("anygen||csrftoken".toRegex())
            }?.count()) ?: 0 >= 2
        ) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}