package ru.nickmiller.tinkofffintech.utils

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Observable


class NetworkUtil(val context: Context) {

    fun isConnected(): Observable<Boolean> {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return Observable.just(activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

}

