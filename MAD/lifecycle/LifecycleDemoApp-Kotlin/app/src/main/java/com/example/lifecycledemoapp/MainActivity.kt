package com.example.lifecycledemoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

/**
 * A single screen that visually logs every Activity Lifecycle callback as it
 * fires, and demonstrates a concrete fix for each of the problems covered
 * in the worksheet:
 *
 *   1. Data Lost on Rotation    -> onSaveInstanceState() + onCreate() restore
 *   2. Duplicate Fetch          -> savedInstanceState == null guard
 *   3. Missing onRestart() log  -> added below
 *   4. Delayed-callback crash   -> isFinishing/isDestroyed check before touching a view
 *   5. Battery / resource drain -> resource started in onResume(), stopped in onPause()
 *   6. Memory leak              -> Handler callbacks cleared in onDestroy()
 *   7. Progress loss (process death) -> wizard step saved in onSaveInstanceState()
 *   8. ViewModel migration      -> fetch guard now lives in FetchDataViewModel
 *   9. Pause-aware timer        -> timestamp-based elapsed-time recalculation
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "com.example.lifecycledemoapp.PREFS"
        private const val KEY_NAME = "saved_name"
        private const val KEY_LAUNCH_COUNT = "launch_count"

        private const val STATE_NAME = "state_name"
        private const val STATE_LOG = "state_log"
        private const val STATE_DEPT = "state_dept"

        private const val STATE_TIMER_START = "state_timer_start"

        // TASK 7 (added): key used to persist which wizard step the user was
        // on, so it survives both rotation and simulated process death.
        private const val STATE_WIZARD_STEP = "state_wizard_step"

        // How long the Task 9 countdown counts down from.
        private const val TIMER_DURATION_SECONDS = 60L
    }

    private lateinit var nameInput: EditText
    private lateinit var counterText: TextView
    private lateinit var taskResultText: TextView
    private lateinit var logText: TextView
    private lateinit var logScroll: ScrollView
    private lateinit var prefs: SharedPreferences

    private lateinit var deptInput: EditText

    // TASK 8 (added): the fetch-guard state now lives here instead of being
    // re-derived from savedInstanceState every time. ViewModelProvider hands
    // back the SAME FetchDataViewModel instance across a rotation-triggered
    // recreation, which is exactly the property that lets us drop the
    // manual Bundle check.
    //
    // NOTE: using ViewModelProvider(this)[...] directly here instead of the
    // `by viewModels()` KTX delegate on purpose - the delegate lives in the
    // androidx.activity:activity-ktx artifact, which some project setups
    // don't pull in, causing "Unresolved reference: viewModels". ViewModelProvider
    // itself lives in androidx.lifecycle:lifecycle-viewmodel, which
    // AppCompatActivity already depends on transitively (it's what makes any
    // AppCompatActivity a ViewModelStoreOwner in the first place), so this
    // works with zero extra Gradle dependencies.
    private val fetchViewModel: FetchDataViewModel by lazy {
        ViewModelProvider(this)[FetchDataViewModel::class.java]
    }

    // TASK 9
    private lateinit var countTimerText: TextView
    private var startTime: Long = 0

    // TASK 7 (added): 0 = step 1, 1 = step 2, 2 = step 3.
    private var currentWizardStep: Int = 0
    private lateinit var step1Layout: LinearLayout
    private lateinit var step2Layout: LinearLayout
    private lateinit var step3Layout: LinearLayout
    private lateinit var wizardStepIndicatorText: TextView

    private val logBuilder = StringBuilder()

    // ---------- Case 3 (Memory Leak) + Case 4 (Battery / Resource Drain) ----------
    // CLEANUP NOTE: the handout had TWO separate Runnables (resourceTickRunnable
    // and sensorRunnable) both posted to the same Handler on the same 3s
    // interval, doing essentially the same job. That's dead-weight duplication,
    // not two different fixes, so it's been merged into ONE Runnable below.
    // This Handler simulates a "sensor"/"camera"-style resource. Task 5 requires
    // it to only run while the screen is actually resumed (start in onResume,
    // stop in onPause -> a matched pair), and Task 6 requires anything still
    // queued on it to be cleared in onDestroy() so nothing outlives this Activity.
    private val resourceHandler = Handler(Looper.getMainLooper())
    private var resourceTicks = 0
    private val resourceTickRunnable = object : Runnable {
        override fun run() {
            resourceTicks++
            log("  [resource] simulated sensor reading #$resourceTicks (running)")
            resourceHandler.postDelayed(this, 3000)
        }
    }

    // TASK 9 (added): a lightweight ticking Runnable that just re-renders the
    // countdown every second WHILE the Activity is resumed. It does not hold
    // any "truth" of its own - updateTimer() always recomputes the remaining
    // time from `startTime`, so this Runnable is purely cosmetic (live UI
    // refresh) and safe to stop/start freely with onPause()/onResume().
    private val timerTickRunnable = object : Runnable {
        override fun run() {
            updateTimer()
            resourceHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameInput = findViewById(R.id.nameInput)
        deptInput = findViewById(R.id.deptInput)

        countTimerText = findViewById(R.id.counterTimerText)

        counterText = findViewById(R.id.counterText)
        taskResultText = findViewById(R.id.taskResultText)
        logText = findViewById(R.id.lifecycleLogText)
        logScroll = findViewById(R.id.logScroll)
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // TASK 7 (added): bind the wizard views added to activity_main.xml.
        step1Layout = findViewById(R.id.step1Layout)
        step2Layout = findViewById(R.id.step2Layout)
        step3Layout = findViewById(R.id.step3Layout)
        wizardStepIndicatorText = findViewById(R.id.wizardStepIndicatorText)

        // ---------- Task 9: Pause-Aware Timer ----------
        // We store a START TIMESTAMP, not a live-decrementing counter. A plain
        // counter only knows how to count while code is actively running on
        // it; the instant the app is backgrounded, execution of that counting
        // code effectively stops being reliable (Handlers get delayed or the
        // process can be deprioritized), so a live counter "freezes" instead
        // of reflecting real-world elapsed time. A timestamp, by contrast,
        // lets us recompute `elapsed = now - startTime` at ANY point in the
        // future and always get the mathematically correct answer, regardless
        // of what happened to the process in between.
        //
        // Restoring: prefer the Bundle value (rotation / simulated process
        // death), otherwise this is a genuinely fresh timer.
        startTime = savedInstanceState?.getLong(STATE_TIMER_START)
            ?: System.currentTimeMillis()
        updateTimer()

        // TASK 7 (added): restore which wizard step we were on. This Bundle
        // is the SAME mechanism used for Task 1 (rotation) but it ALSO
        // survives "Don't keep activities" (simulated process death) because
        // the system persists this Bundle to disk before killing the
        // process, then hands it back to onCreate() when the Activity is
        // recreated. That's the key distinction from Task 8's ViewModel,
        // which only survives rotation, not process death.
        currentWizardStep = savedInstanceState?.getInt(STATE_WIZARD_STEP) ?: 0
        showWizardStep(currentWizardStep)

        // Restore any log lines carried over through rotation, so the log
        // reads as one continuous story instead of resetting each time.
        savedInstanceState?.getString(STATE_LOG)?.let { logBuilder.append(it) }

        log("onCreate  (savedInstanceState == null: ${savedInstanceState == null})")

        // ---------- Task 1: Data Lost on Rotation ----------
        // Prefer the just-rotated value (Bundle) if present; otherwise fall
        // back to the fully-persisted value (SharedPreferences), so the name
        // survives even a full process kill, not just a rotation.
        val nameFromRotation = savedInstanceState?.getString(STATE_NAME)
        val nameFromDisk = prefs.getString(KEY_NAME, "")
        nameInput.setText(nameFromRotation ?: nameFromDisk)

        val deptFromRotation = savedInstanceState?.getString(STATE_DEPT)
        if (deptFromRotation != null) {
            deptInput.setText(deptFromRotation)
        }

        // ---------- Task 8: ViewModel migration (replaces the old Task 2 guard) ----------
        // BEFORE (Task 2): `if (savedInstanceState == null) fetchFromServer()`.
        // That works, but it's re-deriving "have I already fetched?" from a
        // Bundle every single time onCreate() runs. Task 8 asks us to store
        // that fact directly instead, in a ViewModel that the framework keeps
        // alive across rotation - so there's no Bundle check needed at all
        // for this piece of state.
        if (!fetchViewModel.hasFetchedData) {
            fetchFromServer()
            fetchViewModel.hasFetchedData = true
            fetchViewModel.fetchedData = "server-data-fetched-at-${System.currentTimeMillis()}"
            log("  [ViewModel guard] no data in ViewModel yet -> fetched once, cached in ViewModel")
        } else {
            log("  [ViewModel guard] ViewModel already has data (survived rotation) -> fetch skipped")
        }

        // ---------- Task 2: Fresh-launch counter (kept as its own Bundle-guarded example) ----------
        // Left as the original savedInstanceState guard on purpose: this is
        // the "before" picture that Task 8 explicitly contrasts against
        // above, so both approaches are visible side by side in the log.
        if (savedInstanceState == null) {
            val newCount = prefs.getInt(KEY_LAUNCH_COUNT, 0) + 1
            prefs.edit().putInt(KEY_LAUNCH_COUNT, newCount).apply()
            log("  [Bundle guard] fresh launch detected -> counter incremented to $newCount")
        } else {
            log("  [Bundle guard] recreation detected -> counter NOT incremented")
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

        // TASK 7 (added): wire up the wizard's Back/Next buttons.
        findViewById<Button>(R.id.wizardBackButton).setOnClickListener {
            if (currentWizardStep > 0) {
                currentWizardStep--
                showWizardStep(currentWizardStep)
            }
        }
        findViewById<Button>(R.id.wizardNextButton).setOnClickListener {
            if (currentWizardStep < 2) {
                currentWizardStep++
                showWizardStep(currentWizardStep)
            }
        }
    }

    private fun fetchFromServer() {
        log("fetching from server")
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")

        // TASK 9: recompute + start live ticking now that we're back on screen.
        updateTimer()
        resourceHandler.post(timerTickRunnable)

        // Task 5 fix: start the simulated resource only while this screen is
        // actually the one in front of the user.
        resourceHandler.post(resourceTickRunnable)
    }

    override fun onPause() {
        super.onPause()

        // Task 5 fix: stop the simulated resource the instant focus is lost -
        // exactly where a real app would call camera.release() or stop a GPS
        // listener, so nothing keeps draining battery unseen.
        resourceHandler.removeCallbacks(resourceTickRunnable)
        log("onPause  [resource] simulated sensor stopped")

        // TASK 9: stop the cosmetic tick too - no point re-rendering a
        // TextView nobody can see. The underlying `startTime` is untouched,
        // so onResume() will simply recompute the correct elapsed time later.
        resourceHandler.removeCallbacks(timerTickRunnable)

        // Task 6 fix (part 1 of 2): persist to real storage (not just the
        // rotation Bundle) so the name survives even if Android kills this
        // process later.
        prefs.edit().putString(KEY_NAME, nameInput.text.toString()).apply()
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        // TASK 3 (fix): this was the missing log line - onRestart() fires
        // when a STOPPED (backgrounded, not destroyed) Activity is brought
        // back to the foreground, right before onStart(). The handout's
        // MainActivity logged five callbacks but skipped this one.
        log("onRestart")
    }

    override fun onDestroy() {
        // Task 6 fix (part 2 of 2): cancel anything still queued on this
        // Handler so no pending callback keeps holding an implicit reference
        // to this Activity instance (Handler Runnables declared as anonymous/
        // inner classes capture the outer Activity - if one is still sitting
        // in the message queue after the Activity should be gone, the
        // garbage collector can't reclaim the Activity, because that queued
        // message is a live GC root pointing right back at it).
        resourceHandler.removeCallbacksAndMessages(null)
        log("onDestroy  [cleanup] pending callbacks removed, no leaked reference")

        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Task 1 fix: the actual save that lets rotation survive.
        outState.putString(STATE_DEPT, deptInput.text.toString())
        outState.putString(STATE_NAME, nameInput.text.toString())
        outState.putString(STATE_LOG, logBuilder.toString())

        // Task 9 fix: persist the timestamp (not a decremented "seconds
        // left" value) so onCreate() can recompute the correct remaining
        // time on the other side of a rotation OR a simulated process death.
        outState.putLong(STATE_TIMER_START, startTime)

        // TASK 7 (added): persist the wizard step. Combined with the fact
        // that the system also writes this Bundle to disk before killing a
        // backgrounded process, this is what lets the wizard resume from the
        // correct step even under "Don't keep activities" - a plain
        // rotation-only fix (e.g. a field re-read from a static/companion
        // object) would NOT survive that, because the process itself is
        // gone; only data that was actually written into this Bundle comes
        // back.
        outState.putInt(STATE_WIZARD_STEP, currentWizardStep)
    }

    private fun startBackgroundTask() {
        log("  [task] background task started (simulated 5s network call)")
        taskResultText.text = "Task running..."

        // ---------- Case 2 demo: App Crashes (stale callback updates a dead screen) ----------
        // lifecycleScope is tied to this Activity's Lifecycle. If the
        // Activity is destroyed before the delay finishes, this coroutine is
        // automatically cancelled - the line below simply never runs, instead
        // of crashing while trying to update a TextView that no longer exists.
        lifecycleScope.launch {
            delay(5000)
            taskResultText.text = "Task complete!"
            log("  [task] finished safely - screen was still alive to receive it")
        }

        // ---------- Task 4: Fix the Delayed-Callback Crash ----------
        // The crash: rotate before the 5s Handler.postDelayed fires, and the
        // OLD Activity instance is destroyed while the callback is still
        // pending. When it finally runs, `taskResultText` still points at a
        // View that belonged to the destroyed Activity - touching it can
        // crash or silently corrupt state, and either way it's the wrong
        // screen instance (a new one was created by the rotation).
        //
        // The fix checks TWO things before touching the view:
        //   - isFinishing: true once this Activity has begun finishing (e.g.
        //     the user pressed Back) - tells us "don't bother, we're on the
        //     way out."
        //   - isDestroyed: true once onDestroy() has actually completed -
        //     tells us "too late, the Views are already gone/invalid."
        // Checking both covers the window during teardown as well as after
        // it fully completes.
        resourceHandler.postDelayed({
            if (!isFinishing && !isDestroyed) {
                taskResultText.text = "Task complete! (Task 4 delayed callback, lifecycle-safe)"
            } else {
                // Nothing to update - the log below is written via Log/console
                // only in spirit here since `log()` also touches a TextView;
                // in a production app this branch would go to Log.d() instead.
            }
        }, 5000)
    }

    private fun log(message: String) {
        val time = DateFormat.format("HH:mm:ss", Date()).toString()
        logBuilder.append("[$time] $message\n")
        logText.text = logBuilder.toString()
        logScroll.post { logScroll.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun updateTimer() {
        val elapsed = (System.currentTimeMillis() - startTime) / 1000
        val remaining = (TIMER_DURATION_SECONDS - elapsed).coerceAtLeast(0)
        countTimerText.text = "Time remaining: $remaining seconds"
    }

    // TASK 7 (added): toggles which step's LinearLayout is visible and keeps
    // the indicator TextView in sync. This is the implementation of the
    // showStep() stub that was left commented-out in the handout.
    private fun showWizardStep(step: Int) {
        step1Layout.visibility = if (step == 0) View.VISIBLE else View.GONE
        step2Layout.visibility = if (step == 1) View.VISIBLE else View.GONE
        step3Layout.visibility = if (step == 2) View.VISIBLE else View.GONE
        wizardStepIndicatorText.text = "Step ${step + 1} of 3"
    }
}