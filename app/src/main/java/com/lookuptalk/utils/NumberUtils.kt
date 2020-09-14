package com.lb.utils

import java.text.NumberFormat
import java.util.*

fun getFormattedNumber(number: String): String {
    return if (!number.isEmpty()) {
        val value = number.toLong()
        val loc = Locale("en", "in")
        var formattedValue = NumberFormat.getCurrencyInstance(loc).format(value)
        formattedValue = formattedValue.substring(0, formattedValue.indexOf("."))
        formattedValue
    } else {
        "0"
    }
}