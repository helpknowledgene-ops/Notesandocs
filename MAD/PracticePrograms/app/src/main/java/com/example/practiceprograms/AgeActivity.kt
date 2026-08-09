package com.example.practiceprograms
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)


        val birthYearInput: EditText = findViewById(R.id.birthYearInput)
        val ageText: TextView = findViewById(R.id.ageText)
        val calculateButton: Button = findViewById<Button>(R.id.calculateButton)
        val backButton : Button = findViewById(R.id.backButton)

        calculateButton.setOnClickListener {
            val currentAge = calculateAge(birthYearInput);
            ageText.setText(" Age is $currentAge years")
        }

        backButton.setOnClickListener {
            startActivity(Intent( this, MainActivity :: class.java))
            finish()
        }

    }

    private fun calculateAge(birthYearInput: EditText): Int {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val currentAge = currentYear - birthYearInput.text.toString().toInt()

        return currentAge;
    }
}