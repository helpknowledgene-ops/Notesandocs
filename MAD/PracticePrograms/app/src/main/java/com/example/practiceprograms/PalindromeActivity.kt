package com.example.practiceprograms

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PalindromeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palindrome)

        val wordInput: EditText = findViewById(R.id.wordInput)
        val checkButton: Button = findViewById(R.id.checkButton)
        val resultText: TextView = findViewById(R.id.resultText)
        val backButton: Button = findViewById(R.id.backButton)

        checkButton.setOnClickListener {
            val inputText = wordInput.text.toString()

            if (inputText.isEmpty()) {
                Toast.makeText(this, "Please enter a word or phrase", Toast.LENGTH_SHORT).show()
            } else {
                resultText.text = if (isPalindrome(inputText)) {
                    "\"$inputText\" is a palindrome!"
                } else {
                    "\"$inputText\" is not a palindrome."
                }
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val cleaned = text.lowercase().filter { it.isLetterOrDigit() }
        return cleaned == cleaned.reversed()
    }
}
