package com.tsci.core.alert

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.tsci.core.R
import com.tsci.core.extension.waitForDestination
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

abstract class AlertHelper {

    /**
     * Ensures the block will be executed once the component
     * is resumed.
     */
    protected abstract fun whenResumed(block: suspend (Context) -> Unit)

    /**
     * Fetches the nav controller.
     */
    protected abstract fun getNavController(): NavController

    /**
     * Fetches the fragment manager to add result
     * of positive and negative buttons.
     */
    protected abstract fun getFragmentManager(): FragmentManager

    /**
     * Fetches the lifecycle owner to add result
     * of positive and negative buttons.
     */
    protected abstract fun getLifecycleOwner(): LifecycleOwner


    fun showAlert(
        message: String,
        positiveButton: String = "",
        negativeButton: String = "",
        positiveButtonCallback: (() -> Unit)? = null,
        negativeButtonCallback: (() -> Unit)? = null,
        cancelCallback: (() -> Unit)? = null,
        cancelable: Boolean = false
    ) {
        whenResumed {

            val isLoadingVisible =
                getNavController().waitForDestination(R.id.loadingDialogFragment, 0)

            if (isLoadingVisible) {
                getNavController().navigateUp()
            }

            showAlertInternal(
                message,
                positiveButton,
                negativeButton,
                positiveButtonCallback,
                negativeButtonCallback,
                cancelCallback,
                cancelable
            )
        }
    }

    private suspend fun showAlertInternal(
        message: String,
        positiveButton: String = "",
        negativeButton: String = "",
        positiveButtonCallback: (() -> Unit)? = null,
        negativeButtonCallback: (() -> Unit)? = null,
        cancelCallback: (() -> Unit)? = null,
        cancelable: Boolean = false
    ) {
        val bundle = AlertDialogFactory.Builder()
            .setMessage(message)
            .setPositiveButton(positiveButton)
            .setNegativeButton(negativeButton)
            .setCancelable(cancelable)
            .build()

        getNavController().navigate(R.id.nav_graph_alert, bundle)

        val result = suspendCancellableCoroutine {
            getFragmentManager().setFragmentResultListener(
                AlertDialogFragment.REQUEST_KEY_ALERT,
                getLifecycleOwner()
            ) { key, bundle ->
                getFragmentManager().clearFragmentResult(key)
                if (!it.isCompleted) {
                    it.resume(bundle.toAlertResult())
                }
            }
        }
        when (result) {
            AlertResult.POSITIVE_BUTTON_CLICK -> positiveButtonCallback?.invoke()
            AlertResult.NEGATIVE_BUTTON_CLICK -> negativeButtonCallback?.invoke()
            AlertResult.CANCELED -> cancelCallback?.invoke()
        }

    }


}