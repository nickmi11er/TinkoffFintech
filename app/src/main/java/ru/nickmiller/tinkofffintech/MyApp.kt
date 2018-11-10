package ru.nickmiller.tinkofffintech

import android.app.Application
import android.content.Context
import org.koin.android.ext.android.startKoin
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.di.mainModule
import ru.nickmiller.tinkofffintech.di.networkModule


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(mainModule, networkModule))
    }

}