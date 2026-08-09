package com.example.lifecycledemoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.format.DateFormat
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

/**
 * A single screen that visually logs every Activity Lifecycle callback as it
 * fires, and demonstrates a concrete fix for each of the six problems
 * discussed in class:
 *
 *   1. Data Lost on Rotation   -> onSaveInstanceState() + onCreate() restore
 *   2. App Crashes             -> lifecycleScope auto-cancels on destroy
 *   3. Memory Leaks            -> Handler cleared in onDestroy()
 *   4. Battery / Resource Drain-> simulated resource started/stopped with onResume/onPause
 *   5. Duplicate Actions       -> savedInstanceState == null guard
 *   6. User Loses Progress     -> SharedPreferences persists past process death
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "com.example.lifecycledemoapp.PREFS"
        private const val KEY_NAME = "saved_name"
        private const val KEY_LAUNCH_COUNT = "launch_count"

        private const val STATE_NAME = "state_name"
        private const val STATE_LOG = "state_log"
    }

    private lateinit var nameInput: EditText
    private lateinit var counterText: TextView
    private lateinit var taskResultText: TextView
    private lateinit var logText: TextView
    private lateinit var logScroll: ScrollView
    private lateinit var prefs: SharedPreferences

    private val logBuilder = StringBuilder()

    // ---------- Case 3 (Memory Leaks) + Case 4 (Battery / Resource Drain) ----------
    // This Handler simulates a "sensor"/"camera"-style resource. It must only
    // run while the screen is actually resumed, and anything still queued on
    // it must be cleared in onDestroy() so nothing outlives this Activity.
    private val resourceHandler = Handler(Looper.getMainLooper())
    private var resourceTicks = 0
    private val resourceTickRunnable = object : Runnable {
        override fun run() {
            resourceTicks++
            log("  [resource] simulated sensor tick #$resourceTicks (running)")
            resourceHandler.postDelayed(this, 3000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameInput = findViewById(R.id.nameInput)
        counterText = findViewById(R.id.counterText)
        taskResultText = findViewById(R.id.taskResultText)
        logText = findViewById(R.id.lifecycleLogText)
        logScroll = findViewById(R.id.logScroll)
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Restore any log lines carried over through rotation, so the log
        // reads as one continuous story instead of resetting each time.
        savedInstanceState?.getString(STATE_LOG)?.let { logBuilder.append(it) }

        log("onCreate  (savedInstanceState == null: ${savedInstanceState == null})")

        // ---------- Case 1: Data Lost on Rotation ----------
        // Prefer the just-rotated value (Bundle) if present; otherwise fall
        // back to the fully-persisted value (SharedPreferences), so the name
        // survives even a full process kill, not just a rotation.
        val nameFromRotation = savedInstanceState?.getString(STATE_NAME)
        val nameFromDisk = prefs.getString(KEY_NAME, "")
        nameInput.setText(nameFromRotation ?: nameFromDisk)

        // ---------- Case 5: Duplicate Actions ----------
        // Only increment the "fresh launch" counter on a genuinely new
        // launch, never on a rotation-triggered recreation.
        if (savedInstanceState == null) {
            val newCount = prefs.getInt(KEY_LAUNCH_COUNT, 0) + 1
            prefs.edit().putInt(KEY_LAUNCH_COUNT, newCount).apply()
            log("  [guarded action] fresh launch detected -> counter incremented to $newCount")
        } else {
            log("  [guarded action] recreation detected -> counter NOT incremented")
        }
        counterText.text = "Fresh launches recorded: ${prefs.getInt(KEY_LAUNCH_COUNT, 0)}"

        findViewById<Button>(R.id.startTaskButton).setOnClickListener { startBackgroundTask() }
        findViewById<Button>(R.id.goSecondButton).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
        findViewById<Button>(R.id.clearLogButton).setOnClickListener {
            logBuilder.clear()
            logText.text = ""
        }
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
        // Case 4 fix: only start the simulated hardware resource while this
        // screen is actually the one in front of the user.
        resourceHandler.post(resourceTickRunnable)
    }

    override fun onPause() {
        super.onPause()
        // Case 4 fix: stop the simulated resource the instant focus is lost -
        // exactly where a real app would call camera.release() or stop a GPS
        // listener, so nothing keeps draining battery unseen.
        resourceHandler.removeCallbacks(resourceTickRunnable)
        log("onPause  [resource] simulated sensor stopped")

        // Case 6 fix: persist to real storage (not just the rotation Bundle)
        // so the name survives even if Android kills this process later.
        prefs.edit().putString(KEY_NAME, nameInput.text.toString()).apply()
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        log("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        // Case 3 fix: cancel anything still queued on this Handler so no
        // pending callback keeps holding a reference to this Activity.
        resourceHandler.removeCallbacksAndMessages(null)
        log("onDestroy  [cleanup] pending callbacks removed, no leaked reference")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Case 1 fix: the actual save that lets rotation survive.
        outState.putString(STATE_NAME, nameInput.text.toString())
        outState.putString(STATE_LOG, logBuilder.toString())
    }

    private fun startBackgroundTask() {
        log("  [task] background task started (simulated 5s network call)")
        taskResultText.text = "Task running..."

        // ---------- Case 2: App Crashes (stale callback updates a dead screen) ----------
        // lifecycleScope is tied to this Activity's Lifecycle (through
        // LifecycleOwner, implemented up the AppCompatActivity chain). If the
        // Activity is destroyed before the delay finishes, this coroutine is
        // automatically cancelled - the line below simply never runs, instead
        // of crashing while trying to update a TextView that no longer exists.
        lifecycleScope.launch {
            delay(5000)
            taskResultText.text = "Task complete!"
            log("  [task] finished safely - screen was still alive to receive it")
        }
    }

    private fun log(message: String) {
        val time = DateFormat.format("HH:mm:ss", Date()).toString()
        logBuilder.append("[$time] $message\n")
        logText.text = logBuilder.toString()
        logScroll.post { logScroll.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}
