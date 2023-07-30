package com.giovanni.banksampah.helper

import java.text.DecimalFormat

object Helper {
    fun rupiahFormat(price: Int): String {
        val formatter = DecimalFormat("#,###")
        return "Rp " + formatter.format(price.toLong())
    }
}