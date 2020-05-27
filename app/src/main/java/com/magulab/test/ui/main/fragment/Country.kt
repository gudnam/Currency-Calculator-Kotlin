package com.magulab.test.ui.main.fragment

import java.sql.Types

enum class Country(val number: Int) {
    Korea(0),
    Japan(1),
    Philippines(2);

    companion object {
        fun fromInt(value: Int) = Country.values().first { it.number == value }
    }
}
enum class CountryCode(val code: String) {
    Korea("USDKRW"),
    Japan("USDJPY"),
    Philippines("USDPHP")
}
