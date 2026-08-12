package com.example.lifecycledemoapp

import androidx.lifecycle.ViewModel

/**
 * ================= TASK 8 (NEW FILE) =================
 *
 * Task 8 asks us to move the fetched-data guard out of the manual
 * `savedInstanceState == null` check (Task 2's fix) and into a ViewModel
 * instead, so MainActivity no longer needs to reason about Bundles at all
 * for this particular piece of state.
 *
 * WHY THIS WORKS:
 * A ViewModel is retained by the ViewModelStore, which the framework keeps
 * alive across a configuration-change-triggered Activity recreation (the
 * *same* ViewModel instance is handed back by `by viewModels()` after a
 * rotation). That means:
 *   - We never have to ask "is this the first launch or a recreation?"
 *     because the ViewModel itself simply already has (or doesn't have)
 *     the data.
 *   - No Bundle plumbing, no STATE_* keys, no manual save/restore code.
 *
 * WHAT IT DOES NOT SURVIVE (see the "Explain" answer in the worksheet):
 * A ViewModel is scoped to the ViewModelStore, which is cleared when the
 * Activity finishes for real (user presses Back) OR when the whole process
 * is killed by the system (e.g. low memory, or "Don't keep activities").
 * onSaveInstanceState()'s Bundle, on the other hand, is what the framework
 * uses to reconstruct state after process death. So:
 *   - ViewModel survives: rotation / configuration changes.
 *   - ViewModel does NOT survive: process death.
 *   - onSaveInstanceState() DOES survive both rotation and (system-initiated)
 *     process death, because Android persists that Bundle to disk.
 * That's exactly why Task 7 (which must survive simulated process death)
 * is solved with onSaveInstanceState()/restore rather than a ViewModel,
 * while Task 8 (which only needs to survive rotation) is a perfect fit for
 * a ViewModel.
 */
class FetchDataViewModel : ViewModel() {

    /** True once fetchFromServer() has run for this ViewModel's lifetime. */
    var hasFetchedData: Boolean = false

    /** Placeholder for whatever the "server" would have returned. */
    var fetchedData: String? = null
}
