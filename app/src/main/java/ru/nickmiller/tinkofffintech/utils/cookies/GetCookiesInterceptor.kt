package ru.nickmiller.tinkofffintech.utils.cookies

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class GetCookiesInterceptor(val cookiesStore: CookiesStore) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = originalResponse.headers("Set-Cookie")
                .filter { it.contains("anygen|csrftoken".toRegex()) }
                .toHashSet()
            cookiesStore.addCookies(cookies)
        }
        return originalResponse
    }
}