package com.magulab.test.ui.main.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magulab.test.network.RestAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class CurrencyCalculationViewModel: ViewModel() {

    private val TAG = CurrencyCalculationViewModel::class.java.simpleName
    var disposable = CompositeDisposable()

    private var householdApplianceItemList = MutableLiveData<List<String>>()

    fun bindItemList() = householdApplianceItemList

    fun requestItems() {
        RestAPI.requestGetHouseholdAppliances()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                Log.e(TAG, "onError : " + it.message)
            }
            .unsubscribeOn(Schedulers.io())
            .onErrorReturn {
                Log.e(TAG, "onErrorReturn : " + it.message)
                arrayListOf()
            }
            .subscribe { result ->
                householdApplianceItemList.value = result
            }
            .addTo(disposable)
    }

    fun destroyViewModel() {
        disposable.dispose()
    }
}