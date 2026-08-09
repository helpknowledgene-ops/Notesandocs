package com.example.practiceprograms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SimpleInterestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_interest)

        val principalInput: EditText = findViewById(R.id.principalInput)
        val rateInput: EditText = findViewById(R.id.rateInput)
        val timeInput: EditText = findViewById(R.id.timeInput)

        val calculateButton: Button = findViewById(R.id.calculateButton)
        val backButton: Button = findViewById(R.id.backButton)

        val interestText: TextView = findViewById(R.id.interestText)
        val amountText: TextView = findViewById(R.id.amountText)

        calculateButton.setOnClickListener {

            if (principalInput.text.isEmpty() ||
                rateInput.text.isEmpty() ||
                timeInput.text.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter all values",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val principal = principalInput.text.toString().toDouble()
            val rate = rateInput.text.toString().toDouble()
            val time = timeInput.text.toString().toDouble()

            val interest = (principal * rate * time) / 100
            val totalAmount = principal + interest

            interestText.text = "Simple Interest = %.2f".format(interest)
            amountText.text = "Total Amount = %.2f".format(totalAmount)
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}