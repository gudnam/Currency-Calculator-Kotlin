package com.magulab.test.ui.main.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magulab.test.common.convertTimeStampToDateTime
import com.magulab.test.common.price
import com.magulab.test.network.RestAPI
import com.magulab.test.network.data.ExchangeRateData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class CurrencyCalculationViewModel: ViewModel() {

    private val TAG = CurrencyCalculationViewModel::class.java.simpleName

    var disposable = CompositeDisposable()

    private var exchangeRate = MutableLiveData<String>()
    private var inquiryTime = MutableLiveData<String>()
    private var amountReceived = MutableLiveData<String>()

    fun bindExchangeRates() = exchangeRate
    fun bindInquiryTime() = inquiryTime
    fun bindAmountReceived() = amountReceived

    fun initData(usd: Int, country: Country) {
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
                exchangeRate.value = getExchangeRate(parseExchangeRate(result, country), country)
                inquiryTime.value = result.timestamp.convertTimeStampToDateTime()
                amountReceived.value = getAmountReceivedMessage(usd, parseExchangeRate(result, country), country)
            }
            .addTo(disposable)
    }

    fun getExchangeRate(value: Double?, country: Country): String {
        var price = value?.price()
        return "$price ${when (country) {
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

    fun parseExchangeRate(
        data: ExchangeRateData,
        country: Country
    ): Double? {
        return when (country) {
            Country.Korea -> {
                data.quotes[CountryCodeWithUSD.Korea.code]
            }
            Country.Japan -> {
                data.quotes[CountryCodeWithUSD.Japan.code]
            }
            Country.Philippines -> {
                data.quotes[CountryCodeWithUSD.Philippines.code]
            }
        }
    }

    fun textChanged(usd: Int, country: Country) {
        initData(usd, country)
    }

    fun getAmountReceivedMessage(usd: Int, exchangeRate: Double?, country: Country): String? {
        if (usd == 0) return "송금액을 입력해 주세요"

        val calculated: Double = exchangeRate?.times(usd) ?: 0.00
        val price = calculated.price()
        val countryCode = when (country) {
            Country.Korea -> {
                CountryCode.Korea.code
            }
            Country.Japan -> {
                CountryCode.Japan.code
            }
            Country.Philippines -> {
                CountryCode.Philippines.code
            }

        }
        return "수취금액은 $price $countryCode 입니다"
    }
}