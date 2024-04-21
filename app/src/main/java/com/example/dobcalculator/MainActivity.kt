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
   private var tvSelectedDate : TextView? = null
   private var tvAgeInMinutes : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
       tvSelectedDate = findViewById(R.id.tvSelectedDate)
       tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

       btnDatePicker.setOnClickListener {
           clickDatePicker()

       }

    }
    @SuppressLint("SuspiciousIndentation")
    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,

        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this,
                "Year is $selectedYear, month is ${selectedMonth + 1}" +
                        ", day of the month is $selectedDayOfMonth"
                , Toast.LENGTH_SHORT).show()

        val selectedDate = "$selectedDayOfMonth/${selectedMonth +1}/$selectedYear"
        tvSelectedDate?.text = selectedDate

       val sdf =SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

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
        },
        year,
        month,
        day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 8640000
        dpd.show()
    }

}