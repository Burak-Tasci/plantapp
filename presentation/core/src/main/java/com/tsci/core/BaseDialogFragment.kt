package com.tsci.core

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.CallSuper
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

/**
 *
 * Note: The dialog is not full screen by default. To do that,
 * override [isFullScreenDialog] and return true.
 *
 * WARNING: If the fragment is destroyed (a.k.a onDestroyView
 * is called) then the binding will be null.
 */
abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {


    abstract fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): VB

    // The local _binding parameter which is only available
    // within after onCreateView and before onDestroyView.
    private var _binding: VB? = null

    // The property to access within the fragments.
    protected val binding: VB
        get() = _binding!!

    // The TAG value to use in logs.
    protected val TAG = javaClass.simpleName

    /**
     * Override if necessary when the navigated
     * destination (e.g. a nav graph) does not match
     * the actual destination (e.g. the start destination)
     * Override in dialogs that has its start destination,
     * and return the FRAGMENT ID because that is what
     * the navigation controller holds.
     */
    abstract fun getDestinationId(): Int

    /**
     * The function that can be overridden for full-screen dialogs.
     * By default, it returns false.
     */
    open fun isFullScreenDialog() = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (isFullScreenDialog()) {
            with(dialog) {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
            }
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        if (isFullScreenDialog()) {
            dialog?.window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                WindowCompat.setDecorFitsSystemWindows(this, false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val wasDestroyed = arguments?.getBoolean("wasDestroyed", false) == true
        if (wasDestroyed && isCurrentDestination()) {
            runCatching {
                findNavController().navigateUp()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("*****", "$TAG is showing")
    }

    private fun isCurrentDestination(): Boolean {
        return if (isAdded) {
            val destId = findNavController().currentDestination?.id
            val fragmentId = getDestinationId()
            destId == fragmentId
        } else false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // Mark as true, so if the view gets created again,
        // we will know and act for it.
        arguments?.putBoolean("wasDestroyed", true)
    }
}