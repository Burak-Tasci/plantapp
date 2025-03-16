package com.tsci.core.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.tsci.core.BaseDialogFragment
import com.tsci.core.R
import com.tsci.core.databinding.DialogLoadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingDialogFragment: BaseDialogFragment<DialogLoadingBinding>() {

    companion object {
        private const val CANCELLABILITY_DELAY = 10_000L
    }

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): DialogLoadingBinding = DialogLoadingBinding.inflate(inflater, parent, attachToParent)

    override fun isCancelable(): Boolean = false

    override fun isFullScreenDialog(): Boolean = true

    override fun getDestinationId(): Int = R.id.loadingDialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCancelableFalse()
    }

    private fun setCancelableFalse() {
        if (view != null && isAdded) {
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CANCELLABILITY_DELAY)
                isCancelable = true
            }
        }
    }



}