package com.magulab.test

import com.magulab.test.network.data.ExchangeRateData
import com.magulab.test.ui.main.fragment.Country
import com.magulab.test.ui.main.fragment.CountryCode
import com.magulab.test.ui.main.fragment.CountryCodeWithUSD
import com.magulab.test.ui.main.fragment.CurrencyCalculationViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test


class CurrencyCalculationTest {

    // Key를 JNI로 관리하게 되면서 에러 발생
//    @Test
//    fun `calling init data should request api`() {
//        val viewModel = CurrencyCalculationViewModel()
//        mockkObject(RestAPI)
//        viewModel.initData(any())
//        verify(exactly = 1) { RestAPI.requestGetExchangeRate() }
//    }

    @Test
    fun `should return some exchange rate`() {
        val viewModel = CurrencyCalculationViewModel()
        val usdkrw: Double = 123123.00
        val usdjpy: Double = 107.558028
        val usdphp: Double = 50.525026

        val data = ExchangeRateData(hashMapOf(Pair(CountryCodeWithUSD.Korea.code, usdkrw), Pair(CountryCodeWithUSD.Japan.code, usdjpy), Pair(CountryCodeWithUSD.Philippines.code, usdphp)), 0)
        assertEquals(usdkrw, viewModel.parseExchangeRate(data, Country.Korea))
        assertEquals(usdjpy, viewModel.parseExchangeRate(data, Country.Japan))
        assertEquals(usdphp, viewModel.parseExchangeRate(data, Country.Philippines))
    }

    @Test
    fun `should return exchange rate unit`() {
        val viewModel = CurrencyCalculationViewModel()
        val exchangeRate: Double = 100.05
        val exceptedUsdkrw = "KRW / USD"
        val exceptedUsdjpy = "JPY / USD"
        val exceptedUsdphp = "PHP / USD"

        assertEquals("$exchangeRate $exceptedUsdkrw", viewModel.getExchangeRate(exchangeRate, Country.Korea))
        assertEquals("$exchangeRate $exceptedUsdjpy", viewModel.getExchangeRate(exchangeRate, Country.Japan))
        assertEquals("$exchangeRate $exceptedUsdphp", viewModel.getExchangeRate(exchangeRate, Country.Philippines))
    }

    @Test
    fun `should return amount received message`() {
        val viewModel = CurrencyCalculationViewModel()
        val usd = 100
        var krw = 1238.11
        var jpy = 107.71
        var php = 50.67
        val exceptedKrwPrice = "123,811.00"
        val exceptedJpyPrice = "10,771.00"
        val exceptedPhpPrice = "5,067.00"
        val exceptedKrw = "수취금액은 $exceptedKrwPrice ${CountryCode.Korea.code} 입니다"
        val exceptedJpy = "수취금액은 $exceptedJpyPrice ${CountryCode.Japan.code} 입니다"
        val exceptedPhp = "수취금액은 $exceptedPhpPrice ${CountryCode.Philippines.code} 입니다"

        assertEquals(exceptedKrw, viewModel.getAmountReceivedMessage(usd, krw, Country.Korea))
        assertEquals(exceptedJpy, viewModel.getAmountReceivedMessage(usd, jpy, Country.Japan))
        assertEquals(exceptedPhp, viewModel.getAmountReceivedMessage(usd, php, Country.Philippines))
    }
}
