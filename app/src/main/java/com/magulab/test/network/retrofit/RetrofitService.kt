package com.magulab.test.network.retrofit

import com.magulab.test.network.data.ExchangeRateData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("api/live")
    fun requestGetExchangeRates(
        @Query("access_key") accessKey: String
    ): Single<ExchangeRateData>

}