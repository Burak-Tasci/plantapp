package com.tsci.core

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.tsci.core.alert.FragmentAlertHelper
import com.tsci.core.extension.toErrorMessage
import com.tsci.core.loading.FragmentLoadingHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class ViewModelFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment<VB>() {

    abstract val viewModel: VM

    @Inject
    lateinit var loadingHelper: FragmentLoadingHelper

    @Inject
    lateinit var alertHelper: FragmentAlertHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    observeLoadingState()
                }
                launch {
                    observeErrorState()
                }
            }
        }
    }

    private suspend fun observeLoadingState() {
        viewModel.showLoading.collect {
            if (it) {
                loadingHelper.show()
            } else {
                loadingHelper.hide()
            }
        }
    }

    private suspend fun observeErrorState() {
        viewModel.showError.collect { error ->
            alertHelper.showAlert(
                message = error.type.toErrorMessage(requireContext()),
                positiveButton = getString(R.string.general_term_ok)
            )
        }
    }

}