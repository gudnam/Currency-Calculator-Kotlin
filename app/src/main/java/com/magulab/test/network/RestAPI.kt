package com.magulab.test.network

import com.magulab.test.network.retrofit.RetrofitRestAPI
import com.magulab.test.network.retrofit.RetrofitService
import io.reactivex.Single

object RestAPI {

    private val BASE_URL = "http://10.0.2.2:5001/magulab/us-central1/"
    private val TIMEOUT = 10L

    private val service: RetrofitService = RetrofitRestAPI(
        BASE_URL,
        TIMEOUT
    ).getService()

    fun requestGetHouseholdAppliances(): Single<List<String>> {
        return service.requestGetHouseholdAppliances()
    }
}