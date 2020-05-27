package com.magulab.test.ui.main.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.fragment_currency_calculation.*
import java.lang.NumberFormatException


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
        setEvent()
    }

    override fun onResume() {
        super.onResume()
        var remittance = try {
            et_remittance.text.toString().toInt()
        } catch (e: NumberFormatException) {
            0
        }
        viewModel.initData(remittance, Country.fromInt(spinner.selectedItemPosition))
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
    }

    private fun setEvent() {
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var remittance = try {
                    et_remittance.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                viewModel.initData(remittance, Country.fromInt(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        val textWatcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var usd = try {
                    s.toString().toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                viewModel.textChanged(usd, Country.fromInt(spinner.selectedItemPosition))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        et_remittance.addTextChangedListener(textWatcher)
    }

    private fun bindViewModel() {
        viewModel.bindExchangeRates().observe(viewLifecycleOwner, Observer<String> {
            tv_exchange_rate.text = it
        })
        viewModel.bindInquiryTime().observe(viewLifecycleOwner, Observer<String> {
            tv_inquiry_time.text = it
        })
        viewModel.bindAmountReceived().observe(viewLifecycleOwner, Observer<String> {
            tv_amount_received.text = it
        })
    }
}