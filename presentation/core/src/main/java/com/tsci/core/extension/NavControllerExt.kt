package com.tsci.core.extension

import android.util.Log
import androidx.annotation.IdRes
import androidx.navigation.NavController
import com.tsci.core.BuildConfig
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume

/**
 * Waits for a destination to be added for the given milliseconds.
 * Returns true if the current destination matches already, or
 * the destination was indeed added while waiting for the timeout.
 * Otherwise, the method returns false.
 *
 * This function can be called from any kind of coroutine,
 * main thread or background thread.
 *
 * @param destinationId the destination ID to seek for
 * @param duration the maximum duration of the wait
 */
suspend fun NavController.waitForDestination(
    @IdRes destinationId: Int,
    duration: Long
): Boolean {

    // Always use the UI context here.
    // Assigned to coroutineContext directly
    // if called from main thread to support
    // cancellations.
    val uiContext = if (isOnMainThread()) {
        coroutineContext
    } else {
        val coroutineContext = SupervisorJob() + CoroutineExceptionHandler { _, t ->
            if (BuildConfig.DEBUG) {
                throw t
            }
            Log.e("NavController", "waitForDestination", t)
        }
        Dispatchers.Main.immediate + coroutineContext
    }
    val result = withContext(uiContext) {

        // Check the current destination first
        if (currentDestination?.id == destinationId)
            return@withContext true

        // Check the duration. If it's 0, return false immediately.
        val durationInternal = duration.coerceAtLeast(0)
        if (durationInternal == 0L)
            return@withContext false

        // If destination is not added, suspend until the timeout
        // is reached.
        var found = false
        var continuation: CancellableContinuation<Any?>? = null
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->

            // If the destination ID matches, notify the continuation.
            if (destination.id == destinationId && continuation?.isActive == true) {
                found = true
                continuation?.resume(null)
            }
        }

        // Add the listener before the timeout.
        addOnDestinationChangedListener(listener)

        // Make sure the process is not failed (a.k.a timeout
        // did not occur)
        var failed = false

        // Wait inside the runCatching block
        runCatching {
            withTimeout(durationInternal) {
                // Suspend until the timeout
                suspendCancellableCoroutine { cancellableContinuation ->
                    continuation = cancellableContinuation
                }
            }
        }.onFailure {
            failed = true
        }

        // Return the final result.
        return@withContext found && !failed
    }
    coroutineContext.ensureActive()
    return result
}