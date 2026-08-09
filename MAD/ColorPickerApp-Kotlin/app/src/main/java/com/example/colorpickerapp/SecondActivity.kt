package com.example.colorpickerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private var shade: String = "None"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<Button>(R.id.redButton).setOnClickListener { sendResult("Red") }
        findViewById<Button>(R.id.greenButton).setOnClickListener { sendResult("Green") }
        findViewById<Button>(R.id.blueButton).setOnClickListener { sendResult("Blue") }
        findViewById<Button>(R.id.yellowButton).setOnClickListener { sendResult("Yellow") }
        findViewById<Button>(R.id.purpleButton).setOnClickListener { sendResult("Purple") }
        findViewById<Button>(R.id.orangeButton).setOnClickListener { sendResult("Orange") }
        findViewById<Button>(R.id.darkShadeButton).setOnClickListener { shade = "Dark" }
        findViewById<Button>(R.id.lightShadeButton).setOnClickListener { shade = "Light" }

        val cancelButton = findViewById<Button>(R.id.cancelButton)

        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun sendResult(colorName: String) {
        // Build a fresh Intent just to carry the result data back —
        // this is NOT used to start anything, only to return data.
        val resultIntent = Intent()
        resultIntent.putExtra(MainActivity.EXTRA_SELECTED_COLOR, colorName)
        resultIntent.putExtra(MainActivity.EXTRA_SELECTED_THEME, shade)

        // Tell the system this activity finished successfully, and hand
        // the intent to whichever activity is waiting for our result.
        setResult(RESULT_OK, resultIntent)
        finish()
    }

}
