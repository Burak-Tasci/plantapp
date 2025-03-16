package com.tsci.core.loading

import android.util.Log
import androidx.navigation.NavController
import com.tsci.core.R
import com.tsci.core.extension.waitForDestination
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay

abstract class LoadingHelper {

    companion object {
        private val NAV_GRAPH_DIALOG: Int = R.id.nav_graph_loading
        private val FRAGMENT_DIALOG: Int = R.id.loadingDialogFragment
        private const val TAG = "LoadingHelper"
        private const val INITIAL_DELAY_MILLIS = 75L
    }

    /**
     * Determines whether the loading dialog should be open.
     */
    private var shouldBeOpen = false

    /**
     * Ensures the block will be executed once the component
     * is resumed.
     */
    protected abstract fun whenResumed(block: suspend (NavController) -> Unit)


    /**
     * Hides the loading dialog if it is present on the screen.
     */
    fun hide() {
        // If the dialog was not supposed to be open, return.
        if (!shouldBeOpen)
            return

        // Mark as false here. Do not check for state as the device
        // might rotate or the activity / fragment can be created
        // again and the helper can be reset,
        // while the loading dialog itself could remain
        // visible.
        shouldBeOpen = false

        // Execute after code resumes.
        whenResumed { controller ->
            // Wait for one frame: the dialog might have been opening
            // but was not triggered yet.
            awaitFrame()

            // Check the destination. If it matches, navigate up.

            if (controller.waitForDestination(FRAGMENT_DIALOG, 1000)) {
                controller.navigateUp()
            } else {
                Log.i(
                    TAG, "Loading dialog was not open, " +
                            "not attempting to close it."
                )
            }
        }
    }


    /**
     * Shows a loading dialog if no dialogs are present
     * in the screen.
     */
    fun show() {
        // Do not try to open it twice.
        if (shouldBeOpen)
            return

        // Mark as should be open
        shouldBeOpen = true

        // Wait for the fragment to resume
        whenResumed { controller ->

            // Delay a couple milliseconds,
            // cache mechanism might return immediately.
            delay(INITIAL_DELAY_MILLIS)

            // Now, the client could've been called hide()
            // or hideAndWait() in this period.
            // If it gets called before the dialog itself
            // was actually opened, then return immediately.
            if (!shouldBeOpen) {
                Log.i(
                    TAG, "Loading dialog was closed in delay before it was opened."
                )
                return@whenResumed
            }

            // Check if the dialog was already open.
            if (controller.currentDestination?.id == FRAGMENT_DIALOG) {
                Log.i(
                    TAG, "Loading dialog is already open, " +
                            "not navigating to it again."
                )
                return@whenResumed
            }

            controller.navigate(NAV_GRAPH_DIALOG)
        }
    }
}