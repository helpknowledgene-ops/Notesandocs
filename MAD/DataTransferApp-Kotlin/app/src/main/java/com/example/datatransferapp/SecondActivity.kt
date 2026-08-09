package com.example.datatransferapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.datatransferapp.MainActivity.Companion.EXTRA_AGE

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val greetingText = findViewById<TextView>(R.id.greetingText)
        val backButton = findViewById<Button>(R.id.backButton)

        val forwardButton = findViewById<Button>(R.id.forwrdButton)
        // Read the extra back out of the Intent that started this Activity.
        // The elvis operator (?:) supplies a fallback in case the app was
        // somehow launched without that extra (e.g. from a test or a
        // notification), so this never crashes with a null value.
        val receivedName = intent.getStringExtra(MainActivity.EXTRA_NAME) ?: "Guest"
        val age = intent.getIntExtra(EXTRA_AGE, 0)
        greetingText.text = "Hello, $receivedName! Age=$age\n\nThis text was sent from MainActivity via an Intent extra."

        backButton.setOnClickListener {


            // Closes this Activity and returns to MainActivity,
            // which is still on the back stack underneath it.
            finish()
        }

        forwardButton.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)

            intent.putExtra(
                MainActivity.EXTRA_NAME,
                receivedName
            )

            startActivity(intent)
        }
    }
}
