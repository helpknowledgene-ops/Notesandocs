package com.example.datatransferapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.datatransferapp.MainActivity.Companion.EXTRA_AGE

class ThirdActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val greetingText = findViewById<TextView>(R.id.greetingText)
        val backButton = findViewById<Button>(R.id.backButton)

        val receivedName = intent.getStringExtra(MainActivity.EXTRA_NAME) ?: "Guest"

        greetingText.text = "Hello, $receivedName!\n\nThis text was forwarded from SecondActivity via an Intent extra."


        backButton.setOnClickListener {
            finish()
        }


    }
}