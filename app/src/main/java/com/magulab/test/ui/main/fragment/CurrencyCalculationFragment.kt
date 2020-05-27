package com.magulab.test.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.magulab.test.R
import com.magulab.test.common.inflate
import com.magulab.test.network.data.ExchangeRateData
import kotlinx.android.synthetic.main.fragment_currency_calculation.*


class CurrencyCalculationFragment : Fragment() {
    private val TAG = CurrencyCalculationFragment::class.java.simpleName

    private lateinit var items: Array<String>
    private lateinit var viewModel: CurrencyCalculationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CurrencyCalculationViewModel::class.java)
        items = resources.getStringArray(R.array.currency_array)
        bindViewModel()
        return container?.inflate(R.layout.fragment_currency_calculation)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData(Country.fromInt(spinner.selectedItemPosition))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.destroyViewModel()
    }

    private fun initView() {
        tv_remittance_country.text = getString(R.string.usd)
        val adapter = ArrayAdapter(context!!, R.layout.currency_spinner_item, items)
        adapter.setDropDownViewResource(R.layout.currency_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.initData(Country.fromInt(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }

    private fun bindViewModel() {
        viewModel.bindExchangeRates().observe(viewLifecycleOwner, Observer<String> {
            tv_exchange_rate.text = it
        })
        viewModel.bindInquiryTime().observe(viewLifecycleOwner, Observer<String> {
            tv_inquiry_time.text = it
        })
    }
}