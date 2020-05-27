package com.magulab.test

import com.magulab.test.network.data.ExchangeRateData
import com.magulab.test.ui.main.fragment.Country
import com.magulab.test.ui.main.fragment.CurrencyCalculationViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test


class CurrencyCalculationTest {

    @Test
    fun `should return the amount of received`() {
        val viewModel = CurrencyCalculationViewModel()
        val remittance = 100
        val exchangeRate = 1130.05f
        assertEquals(remittance*exchangeRate, viewModel.calculate(remittance, exchangeRate))
    }

    // Key를 JNI로 관리하게 되면서 에러 발생
//    @Test
//    fun `calling init data should request api`() {
//        val viewModel = CurrencyCalculationViewModel()
//        mockkObject(RestAPI)
//        viewModel.initData(Country.Korea)
//        verify(exactly = 1) { RestAPI.requestGetExchangeRate() }
//    }

    @Test
    fun `should return some exchange rate`() {
        val viewModel = CurrencyCalculationViewModel()
        val usdkrw: Double = 123123.00
        val usdjpy: Double = 107.558028
        val usdphp: Double = 50.525026
        val exceptedUsdkrw: Float = 123123.00f
        val exceptedUsdjpy: Float = 107.55f
        val exceptedUsdphp: Float = 50.52f

        val data = ExchangeRateData(hashMapOf(Pair("USDKRW", usdkrw), Pair("USDJPY", usdjpy), Pair("USDPHP", usdphp)))
        assertEquals(exceptedUsdkrw, viewModel.parseExchangeRate(data, Country.Korea))
        assertEquals(exceptedUsdjpy, viewModel.parseExchangeRate(data, Country.Japan))
        assertEquals(exceptedUsdphp, viewModel.parseExchangeRate(data, Country.Philippines))
    }

    @Test
    fun `should return exchange rate unit`() {
        val viewModel = CurrencyCalculationViewModel()
        val exchangeRate = 100f
        val exceptedUsdkrw = "KRW / USD"
        val exceptedUsdjpy = "JPY / USD"
        val exceptedUsdphp = "PHP / USD"

        assertEquals("$exchangeRate $exceptedUsdkrw", viewModel.getExchangeRate(exchangeRate, Country.Korea))
        assertEquals("$exchangeRate $exceptedUsdjpy", viewModel.getExchangeRate(exchangeRate, Country.Japan))
        assertEquals("$exchangeRate $exceptedUsdphp", viewModel.getExchangeRate(exchangeRate, Country.Philippines))
    }
}
