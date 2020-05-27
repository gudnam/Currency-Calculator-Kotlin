package com.magulab.test.ui.main.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magulab.test.common.convertTimeStampToDateTime
import com.magulab.test.network.RestAPI
import com.magulab.test.network.data.ExchangeRateData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.sql.Timestamp

class CurrencyCalculationViewModel: ViewModel() {

    private val TAG = CurrencyCalculationViewModel::class.java.simpleName

    var disposable = CompositeDisposable()

    private var exchangeRate = MutableLiveData<String>()
    private var inquiryTime = MutableLiveData<String>()

    fun bindExchangeRates() = exchangeRate
    fun bindInquiryTime() = inquiryTime

    fun initData(country: Country) {
        RestAPI.requestGetExchangeRate()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                Log.e(TAG, "onError : " + it.message)
            }
            .unsubscribeOn(Schedulers.io())
            .onErrorReturn {
                Log.e(TAG, "onErrorReturn : " + it.message)
                ExchangeRateData(hashMapOf(), 0)
            }
            .subscribe { result ->
                Log.i("TEST", "${result.timestamp.convertTimeStampToDateTime()}")
                exchangeRate.value = getExchangeRate(parseExchangeRate(result, country), country)
                inquiryTime.value = result.timestamp.convertTimeStampToDateTime()
            }
            .addTo(disposable)
    }

    fun getExchangeRate(value: Float, country: Country): String {
         return "$value ${when (country) {
            Country.Korea -> {
                "KRW / USD"
            }
            Country.Japan -> {
                "JPY / USD"
            }
            Country.Philippines -> {
                "PHP / USD"
            }
        }
        }"
    }

    fun destroyViewModel() {
        disposable.dispose()
    }

    fun calculate(remittance: Int, exchangeRate: Float): Float {
        return exchangeRate*remittance
    }

    fun parseExchangeRate(
        data: ExchangeRateData,
        country: Country
    ): Float {
        val result: Double? = when (country) {
            Country.Korea -> {
                data.quotes[CountryCode.Korea.code]
            }
            Country.Japan -> {
                data.quotes[CountryCode.Japan.code]
            }
            Country.Philippines -> {
                data.quotes[CountryCode.Philippines.code]
            }
        }
        return kotlin.math.floor((result?.toFloat() ?: 0.0f) * 100) / 100
    }
}