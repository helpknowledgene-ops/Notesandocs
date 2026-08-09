package com.example.practiceprograms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBmi: Button = findViewById(R.id.btnBmi)
        val btnTemperature: Button = findViewById(R.id.btnTemperature)
        val btnCalculator: Button = findViewById(R.id.btnCalculator)
        val btnPalindrome: Button = findViewById(R.id.btnPalindrome)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnSimpleInterest: Button = findViewById(R.id.btnSimpleInterest)
        btnBmi.setOnClickListener {
            startActivity(Intent(this, BmiActivity::class.java))
        }

        btnTemperature.setOnClickListener {
            startActivity(Intent(this, TemperatureActivity::class.java))
        }

        btnCalculator.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }

        btnPalindrome.setOnClickListener {
            startActivity(Intent(this, PalindromeActivity::class.java))
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }



        btnSimpleInterest.setOnClickListener {
            startActivity(Intent(this, SimpleInterestActivity::class.java))
        }

        val btnEvenOdd: Button = findViewById(R.id.btnEvenOdd)

        btnEvenOdd.setOnClickListener {
            startActivity(Intent(this, EvenOddActivity::class.java))
        }

        val btnGrade: Button = findViewById(R.id.btnGrade)

        btnGrade.setOnClickListener {
            startActivity(Intent(this, GradeActivity::class.java))
        }

        val btnAge : Button = findViewById(R.id.btnAge)

        btnAge.setOnClickListener {
            startActivity(Intent(this, AgeActivity :: class.java ))
        }

        val multiConvertorButton : Button = findViewById(R.id.btnMultiConverter)

        multiConvertorButton.setOnClickListener {
            startActivity(Intent(this, MultiConverterActivity :: class.java))
        }

    }
}
