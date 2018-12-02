package ru.nickmiller.tinkofffintech.utils

fun suffixOfNum(num: Int?): String {
    if (num != null) {
        val num = num % 100
        if (num == 0) return "й"
        if (num in 11..20) return "й"
        return when ((num.rem(10))) {
            0, in 5..9 -> "й"
            1 -> "е"
            in 2..4 -> "я"
            else -> ""
        }
    } else {
        return ""
    }
}