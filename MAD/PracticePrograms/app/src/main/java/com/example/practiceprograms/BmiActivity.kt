package com.example.practiceprograms

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BmiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        val heightInput: EditText = findViewById(R.id.heightInput)
        val weightInput: EditText = findViewById(R.id.weightInput)
        val calculateButton: Button = findViewById(R.id.calculateButton)
        val bmiValueText: TextView = findViewById(R.id.bmiValueText)
        val bmiCategoryText: TextView = findViewById(R.id.bmiCategoryText)
        val backButton: Button = findViewById(R.id.backButton)

        calculateButton.setOnClickListener {
            val heightText = heightInput.text.toString()
            val weightText = weightInput.text.toString()

            if (heightText.isEmpty() || weightText.isEmpty()) {
                Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_SHORT).show()
            } else {
                val height = heightText.toDoubleOrNull()
                val weight = weightText.toDoubleOrNull()

                if (height == null || weight == null || height <= 0) {
                    Toast.makeText(this, "Enter valid numbers", Toast.LENGTH_SHORT).show()
                } else {
                    val bmi = calculateBmi(weight, height)
                    val category = getBmiCategory(bmi)

                    bmiValueText.text = "BMI: %.2f".format(bmi)
                    bmiCategoryText.text = "Category: $category"
                }
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun calculateBmi(weight: Double, height: Double): Double {
        return weight / (height * height)
    }

    private fun getBmiCategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }
    }
}
