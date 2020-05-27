package com.magulab.test.network.retrofit

import com.magulab.test.network.data.HouseholdApplianceData
import io.reactivex.Single
import retrofit2.http.GET

interface RetrofitService {

    @GET("householdAppliances")
    fun requestGetHouseholdAppliances(): Single<List<String>>

}