package com.example.practiceprograms

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    private lateinit var number1Input: EditText
    private lateinit var number2Input: EditText
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        number1Input = findViewById(R.id.number1Input)
        number2Input = findViewById(R.id.number2Input)
        resultText = findViewById(R.id.resultText)

        val addButton: Button = findViewById(R.id.addButton)
        val subtractButton: Button = findViewById(R.id.subtractButton)
        val multiplyButton: Button = findViewById(R.id.multiplyButton)
        val divideButton: Button = findViewById(R.id.divideButton)
        val backButton: Button = findViewById(R.id.backButton)

        addButton.setOnClickListener { calculate("+") }
        subtractButton.setOnClickListener { calculate("-") }
        multiplyButton.setOnClickListener { calculate("*") }
        divideButton.setOnClickListener { calculate("/") }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun calculate(operation: String) {
        val text1 = number1Input.text.toString()
        val text2 = number2Input.text.toString()

        if (text1.isEmpty() || text2.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show()
            return
        }

        val num1 = text1.toDoubleOrNull()
        val num2 = text2.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            Toast.makeText(this, "Enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> {
                if (num2 == 0.0) {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                    return
                }
                num1 / num2
            }
            else -> return
        }

        resultText.text = "Result: %.2f".format(result)
    }
}
