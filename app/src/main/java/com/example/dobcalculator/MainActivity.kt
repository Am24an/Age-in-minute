package com.example.dobcalculator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null
    private var tvCurrentAge: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvCurrentAge = findViewById(R.id.tvCurrentAge)

        btnDatePicker.setOnClickListener {
            clickDatePicker()

        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,

            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(
                    this,
                    "Year is $selectedYear, month is ${selectedMonth + 1}" +
                            ", day of the month is $selectedDayOfMonth", Toast.LENGTH_SHORT
                ).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)
                theDate?.let {

                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        tvAgeInMinutes?.text = differenceInMinutes.toString()

                    }

                }
                // call currentAge to calculate and display the age
                currentAge(selectedDate)
            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 8640000
        dpd.show()
    }


    @SuppressLint("SetTextI18n")
    private fun currentAge(selectedDate: String) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val theDate = sdf.parse(selectedDate)

        theDate?.let {
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.time = theDate

            val currentCalendar = Calendar.getInstance()

            var years = currentCalendar.get(Calendar.YEAR) - selectedCalendar.get(Calendar.YEAR)
            var months = currentCalendar.get(Calendar.MONTH) - selectedCalendar.get(Calendar.MONTH)
            var days = currentCalendar.get(Calendar.DAY_OF_MONTH) - selectedCalendar.get(Calendar.DAY_OF_MONTH)

            // Adjust for negative days
            if (days < 0) {
                months--
                days += selectedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }

            // Adjust for negative months
            if (months < 0) {
                years--
                months += 12
            }

            // Display the result in tvCurrentAge
            tvCurrentAge?.text = "$years Years, $months Months, and $days Days"
        }
    }


}