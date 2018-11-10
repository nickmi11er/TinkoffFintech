package ru.nickmiller.tinkofffintech.utils.cookies

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddCookiesInterceptor(val cookiesStore: CookiesStore) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = cookiesStore.getCookies()
        cookies?.forEach {
            builder.addHeader("Cookie", it)
        }
        return chain.proceed(builder.build())
    }
}