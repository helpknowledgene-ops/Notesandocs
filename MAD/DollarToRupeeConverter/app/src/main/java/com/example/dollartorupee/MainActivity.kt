package com.example.dollartorupee

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private val exchangeRate: Double = 96.50


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dollarInput: EditText = findViewById(R.id.dollarInput)
        val convertButton: Button = findViewById(R.id.convertButton)
        val resultText: TextView = findViewById(R.id.resultText)
        val rateText: TextView = findViewById(R.id.rateText)


        rateText.text = "Rate used: 1 USD = ₹%.2f".format(exchangeRate)


        convertButton.setOnClickListener {
            val inputText = dollarInput.text.toString()

            if (inputText.isEmpty()) {

                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            } else {

                val dollars = inputText.toDoubleOrNull()

                if (dollars == null) {
                    Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show()
                } else {
                    val rupees = convertToRupees(dollars)
                    resultText.text = "$%.2f = ₹%.2f".format(dollars, rupees)
                }
            }
        }
    }


    private fun convertToRupees(dollars: Double): Double {
        return dollars * exchangeRate
    }
}
