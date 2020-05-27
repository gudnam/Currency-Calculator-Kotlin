package com.magulab.test.network

import android.util.Log
import com.magulab.test.network.data.ExchangeRateData
import com.magulab.test.network.retrofit.RetrofitRestAPI
import com.magulab.test.network.retrofit.RetrofitService
import io.reactivex.Single

object RestAPI {

    private val BASE_URL = "http://www.apilayer.net/"
    private val TIMEOUT = 10L

    init {
        System.loadLibrary("keys");
    }
    private external fun getKey(): String

    private val service: RetrofitService = RetrofitRestAPI(
        BASE_URL,
        TIMEOUT
    ).getService()

    fun requestGetExchangeRate(): Single<ExchangeRateData> {
        return service.requestGetExchangeRates(getKey())
    }
}