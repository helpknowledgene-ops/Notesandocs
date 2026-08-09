package com.example.practiceprograms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MultiConverterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_converter)


        val backButton : Button = findViewById(R.id.backButton)
        val converterSpinner : Spinner = findViewById(R.id.converterSpinner)
        val valueInput : EditText = findViewById(R.id.valueInput)
        val convertButton : Button = findViewById(R.id.convertButton)
        val resultText : TextView = findViewById(R.id.resultText)

        val options = arrayOf(
            "Km to Miles",
            "Kg to Pounds",
            "Celsius to Fahrenheit"
        )



        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity :: class.java))
            finish()
        }
    }
}