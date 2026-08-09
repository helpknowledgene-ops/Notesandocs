package com.example.practiceprograms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GradeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade)

        val marksInput: EditText = findViewById(R.id.marksInput)
        val calculateButton: Button = findViewById(R.id.calculateButton)
        val gradeText: TextView = findViewById(R.id.gradeText)
        val backButton: Button = findViewById(R.id.backButton)

        calculateButton.setOnClickListener {

            if (marksInput.text.isEmpty()) {
                Toast.makeText(this, "Enter marks", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val marks = marksInput.text.toString().toInt()

            if (marks < 0 || marks > 100) {
                Toast.makeText(this, "Marks should be between 0 and 100", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val grade = when {
                marks >= 90 -> "A"
                marks >= 75 -> "B"
                marks >= 60 -> "C"
                marks >= 40 -> "D"
                else -> "F"
            }

            gradeText.text = "Grade : $grade"
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}