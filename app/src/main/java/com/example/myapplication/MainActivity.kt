package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var amountEditTextFrom: EditText
    private lateinit var amountEditTextTo: EditText
    private lateinit var currencySpinnerFrom: Spinner
    private lateinit var currencySpinnerTo: Spinner

    private var isUpdatingText = false

    private val EXCHANGE_RATES = mapOf(
        "VND" to 1.0,
        "USD" to 26088.0,
        "EUR" to 29579.62,
        "JPY" to 162.75,
        "GBP" to 32250.0,
        "AUD" to 16610.0,
        "SGD" to 18700.0,
        "CAD" to 18162.0,
        "CHF" to 31786.0,
        "CNY" to 3602.0,
        "KRW" to 15.68,
        "GBP" to 33653.29,
        "HKD" to 3288.73,
        "THB" to 716.40


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        amountEditTextFrom = findViewById(R.id.editTextAmount1)
        amountEditTextTo = findViewById(R.id.editTextAmount2)
        currencySpinnerFrom = findViewById(R.id.spinnerCurrency1)
        currencySpinnerTo = findViewById(R.id.spinnerCurrency2)

        val currencies = EXCHANGE_RATES.keys.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        currencySpinnerFrom.adapter = adapter
        currencySpinnerTo.adapter = adapter

        currencySpinnerFrom.setSelection(currencies.indexOf("VND"))
        currencySpinnerTo.setSelection(currencies.indexOf("USD"))

        // TextWatcher: nhập ở ô trên
        amountEditTextFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateConversion(isFromInput = true)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // TextWatcher: nhập ở ô dưới
        amountEditTextTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateConversion(isFromInput = false)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateConversion(isFromInput = true)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        currencySpinnerFrom.onItemSelectedListener = spinnerListener
        currencySpinnerTo.onItemSelectedListener = spinnerListener
    }

    private fun updateConversion(isFromInput: Boolean) {
        if (isUpdatingText) return
        isUpdatingText = true

        val editSource = if (isFromInput) amountEditTextFrom else amountEditTextTo
        val editTarget = if (isFromInput) amountEditTextTo else amountEditTextFrom

        val spinnerSource = if (isFromInput) currencySpinnerFrom else currencySpinnerTo
        val spinnerTarget = if (isFromInput) currencySpinnerTo else currencySpinnerFrom

        val amount = editSource.text.toString().toDoubleOrNull()
        if (amount == null) {
            editTarget.setText("")
            isUpdatingText = false
            return
        }

        val fromCurrency = spinnerSource.selectedItem.toString()
        val toCurrency = spinnerTarget.selectedItem.toString()

        val result = convertCurrency(amount, fromCurrency, toCurrency)
        editTarget.setText(formatCurrency(result))

        isUpdatingText = false
    }

    private fun convertCurrency(amount: Double, from: String, to: String): Double {
        val fromRate = EXCHANGE_RATES[from] ?: 1.0
        val toRate = EXCHANGE_RATES[to] ?: 1.0
        return amount * fromRate / toRate
    }

    private fun formatCurrency(amount: Double): String {
        return NumberFormat.getNumberInstance(Locale.US).apply {
            maximumFractionDigits = 4
        }.format(amount)
    }
}