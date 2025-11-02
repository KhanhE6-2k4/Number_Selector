package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etBirthDay: EditText
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var calendarView: CalendarView
    private lateinit var cbTerms: CheckBox
    private lateinit var btnSelect: Button
    private lateinit var btnRegister: Button

    private var isCalendarVisible = false
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_layout)

        initViews()

        setupListeners()
    }
    private fun initViews() {
        etFirstName = findViewById<EditText>(R.id.etFirstName)
        etLastName = findViewById<EditText>(R.id.etLastName)
        etBirthDay = findViewById<EditText>(R.id.etBirthDay)
        etAddress = findViewById<EditText>(R.id.etAddress)
        etEmail = findViewById<EditText>(R.id.etEmail)
        rgGender = findViewById<RadioGroup>(R.id.rgGender)
        rbMale = findViewById<RadioButton>(R.id.rbMale)
        rbFemale = findViewById<RadioButton>(R.id.rbFemale)
        calendarView = findViewById<CalendarView>(R.id.calendarView)
        cbTerms = findViewById<CheckBox>(R.id.cbTerms)
        btnSelect = findViewById<Button>(R.id.btnSelect)
        btnRegister = findViewById<Button>(R.id.btnRegister)
        calendarView.visibility = View.GONE // ẩn calendar khi vào ứng dụng
    }

    private fun setupListeners() {
        btnSelect.setOnClickListener {
            toggleCalendar()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            etBirthDay.setText(selectedDate)
            resetBackground(etBirthDay)
            toggleCalendar()
        }
        // Reset background when user starts typing/selecting
        etFirstName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                resetBackground(etFirstName)
        }

        etLastName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                resetBackground(etLastName)
        }

        etBirthDay.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                resetBackground(etBirthDay)
        }

        etAddress.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                resetBackground(etAddress)
        }

        etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                resetBackground(etEmail)
        }

        rgGender.setOnCheckedChangeListener { _, hasFocus ->
            resetBackground(rgGender)
        }

        cbTerms.setOnCheckedChangeListener { _, hasFocus ->
            if (hasFocus)
                resetBackground(cbTerms)
        }
        // Register button
        btnRegister.setOnClickListener {
            validateAndRegister()
        }

    }

    private fun toggleCalendar() {
        isCalendarVisible = if (isCalendarVisible) {
            calendarView.visibility = View.GONE
            false
        }
        else {
            calendarView.visibility = View.VISIBLE
            true
        }
    }

    private fun validateAndRegister() {
        var isValid = true

        // Validate First name
        if (etFirstName.text.toString().trim().isEmpty()) {
            setErrorBackground(etFirstName)
            isValid = false
        }

        // Validate Last name
        if (etLastName.text.toString().trim().isEmpty()) {
            setErrorBackground(etLastName)
            isValid = false
        }

        // Validate Gender
        if (rgGender.checkedRadioButtonId == -1) {
            setErrorBackground(rgGender)
            isValid = false
        }

        // Validate Birthday
        if (etBirthDay.text.toString().trim().isEmpty()) {
            setErrorBackground(etBirthDay)
            isValid = false
        }

        // Validate Address
        if (etAddress.text.toString().trim().isEmpty()) {
            setErrorBackground(etAddress)
            isValid = false
        }

        // Validate Email
        if (etEmail.text.toString().trim().isEmpty()) {
            setErrorBackground(etEmail)
            isValid = false
        }

        // Validate Terms
        if (!cbTerms.isChecked) {
            setErrorBackground(cbTerms)
            isValid = false
        }

        if (isValid) {
            Toast.makeText(this, "The register proccess has been successfully", Toast.LENGTH_SHORT).show()
            // Map and process data
        }
        else {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSelectedGender(): String {
        return when (rgGender.checkedRadioButtonId) {
            R.id.rbMale -> "Male"
            R.id.rbFemale -> "Female"
            else -> ""
        }
    }

    private fun setErrorBackground(view: View) {
        view.setBackgroundColor((Color.parseColor("#FFCCCC")))
    }

    private fun resetBackground(view: View) {
        view.setBackgroundColor(Color.parseColor("#EEEEEE"))
    }

}
