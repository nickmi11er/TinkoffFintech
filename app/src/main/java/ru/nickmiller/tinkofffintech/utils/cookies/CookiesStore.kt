package ru.nickmiller.tinkofffintech.utils.cookies

import android.content.Context
import ru.nickmiller.tinkofffintech.MyApp
import ru.nickmiller.tinkofffintech.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet


class CookiesStore(val context: Context) {

    private val COOKIES_KEY = "cookies"
    val sdf = SimpleDateFormat("d-MMM-yyyy HH:mm:ss", Locale.getDefault())
    val expiresPatter =
        "expires=.+(\\d{2}-\\w{3}-\\d{4}\\s\\d{2}:\\d{2}:\\d{2})".toRegex(RegexOption.IGNORE_CASE)

    fun getCookies(): MutableSet<String>? {
        val prefs = context.getSharedPreferences(
            context.getString(R.string.sp_cookies),
            Context.MODE_PRIVATE
        )
        val cookies = prefs.getStringSet(COOKIES_KEY, HashSet<String>())
        val freshCookies = cookies?.let { clearExpires(it) }

        freshCookies?.let { setCookies(freshCookies) }

        return freshCookies
    }

    fun setCookies(cookies: Set<String>): Boolean {
        val mcpPreferences = context.getSharedPreferences(
            context.getString(R.string.sp_cookies),
            Context.MODE_PRIVATE
        )
        val editor = mcpPreferences.edit()
        return editor.putStringSet(COOKIES_KEY, cookies).commit()
    }

    fun clearCookies(): Boolean {
        val mcpPreferences = context.getSharedPreferences(
            context.getString(R.string.sp_cookies),
            Context.MODE_PRIVATE
        )
        val editor = mcpPreferences.edit()
        return editor.clear().commit()
    }

    fun addCookies(cookies: HashSet<String>): Boolean {
        val oldCookies = getCookies()
        oldCookies?.let { cookies.addAll(it) }
        val mcpPreferences = context.getSharedPreferences(
            context.getString(R.string.sp_cookies),
            Context.MODE_PRIVATE
        )
        val editor = mcpPreferences.edit()
        return editor.putStringSet(COOKIES_KEY, cookies).commit()
    }

    fun clearExpires(cookies: Set<String>): HashSet<String> {
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.DATE, 1)

        return cookies.filter {
            val match = expiresPatter.find(it)?.groups?.get(1)
            if (match != null) {
                val expireDate = sdf.parse(match.value)
                expireDate.after(cal.time)
            } else {
                false
            }
        }.toHashSet()
    }
}