package com.magulab.test.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.magulab.test.R
import com.magulab.test.ui.main.fragment.CurrencyCalculationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.cl_currency_calculation, CurrencyCalculationFragment()).addToBackStack(null).commit()
    }

}
