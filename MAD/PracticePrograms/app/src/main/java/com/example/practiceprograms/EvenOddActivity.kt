package com.example.practiceprograms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EvenOddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_even_odd)

        val numberInput: EditText = findViewById(R.id.numberInput)
        val checkButton: Button = findViewById(R.id.checkButton)
        val resultText: TextView = findViewById(R.id.resultText)
        val backButton: Button = findViewById(R.id.backButton)

        checkButton.setOnClickListener {

            if (numberInput.text.isEmpty()) {
                Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val number = numberInput.text.toString().toInt()

            if (number % 2 == 0) {
                resultText.text = "$number is Even"
            } else {
                resultText.text = "$number is Odd"
            }
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}