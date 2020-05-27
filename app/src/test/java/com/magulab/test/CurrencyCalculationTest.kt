package com.magulab.test

import com.magulab.test.network.RestAPI
import com.magulab.test.ui.main.fragment.CurrencyCalculationViewModel
import io.mockk.*
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

    @Test
    fun `calling init data should request api`() {
        val viewModel = CurrencyCalculationViewModel()
        mockkObject(RestAPI)
        viewModel.initData()
        verify(exactly = 1) { RestAPI.requestGetExchangeRate() }
    }
}
