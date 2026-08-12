package com.example.lifecycledemoapp

import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

/**
 * A second screen with its own lifecycle log, so students can see the exact
 * cross-activity handoff sequence: MainActivity.onPause() -> SecondActivity
 * onCreate/onStart/onResume -> MainActivity.onStop() -- the same sequence
 * shown in the lifecycle diagram covered in class.
 *
 * NO CHANGES MADE: none of Tasks 1-9 in the worksheet call for edits here.
 * It's left exactly as provided in the handout.
 */
class SecondActivity : AppCompatActivity() {

    private lateinit var logText: TextView
    private lateinit var logScroll: ScrollView
    private val logBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        logText = findViewById(R.id.lifecycleLogText)
        logScroll = findViewById(R.id.logScroll)

        log("onCreate")

        findViewById<Button>(R.id.backButton).setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume  -> switch back to MainActivity now and check its log")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        val time = DateFormat.format("HH:mm:ss", Date()).toString()
        logBuilder.append("[$time] $message\n")
        logText.text = logBuilder.toString()
        logScroll.post { logScroll.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}
