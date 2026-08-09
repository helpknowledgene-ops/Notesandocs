package com.example.datatransferapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        // Keys used for passing data through the Intent
        const val EXTRA_NAME = "com.example.datatransferapp.EXTRA_NAME"
        const val EXTRA_AGE = "com.example.datatransferapp.EXTRA_AGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val ageInput = findViewById<EditText>(R.id.ageInput)
        val sendButton = findViewById<Button>(R.id.sendButton)

        sendButton.setOnClickListener {

            // Read input values
            val name = nameInput.text.toString().trim()
            val ageText = ageInput.text.toString().trim()

            // Validate name
            if (name.isBlank()) {
                Toast.makeText(
                    this,
                    "Please enter your name",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Validate age
            val age = ageText.toIntOrNull()

            if (age == null) {
                Toast.makeText(
                    this,
                    "Please enter a valid age",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Create Intent
            val intent = Intent(this, SecondActivity::class.java)

            // Send data using Intent extras
            intent.putExtra(EXTRA_NAME, name)
            intent.putExtra(EXTRA_AGE, age)

            // Open SecondActivity
            startActivity(intent)
        }
    }
}