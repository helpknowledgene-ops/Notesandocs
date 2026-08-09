package com.example.practiceprograms

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TemperatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)

        val temperatureInput: EditText = findViewById(R.id.temperatureInput)
        val directionGroup: RadioGroup = findViewById(R.id.directionGroup)
        val convertButton: Button = findViewById(R.id.convertButton)
        val resultText: TextView = findViewById(R.id.resultText)
        val backButton: Button = findViewById(R.id.backButton)

        convertButton.setOnClickListener {
            val inputText = temperatureInput.text.toString()

            if (inputText.isEmpty()) {
                Toast.makeText(this, "Please enter a temperature", Toast.LENGTH_SHORT).show()
            } else {
                val value = inputText.toDoubleOrNull()

                if (value == null) {
                    Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show()
                } else {
                    if (directionGroup.checkedRadioButtonId == R.id.radioCtoF) {
                        val fahrenheit = celsiusToFahrenheit(value)
                        resultText.text = "%.2f°C = %.2f°F".format(value, fahrenheit)
                    } else {
                        val celsius = fahrenheitToCelsius(value)
                        resultText.text = "%.2f°F = %.2f°C".format(value, celsius)
                    }
                }
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun celsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }

    private fun fahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }
}
