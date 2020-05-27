package com.magulab.test.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.magulab.test.R
import com.magulab.test.common.inflate
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
        viewModel.requestItems()
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

    private fun bindViewModel() {
        viewModel.bindItemList().observe(viewLifecycleOwner, Observer<List<String>> { newItems ->
        })
    }
}