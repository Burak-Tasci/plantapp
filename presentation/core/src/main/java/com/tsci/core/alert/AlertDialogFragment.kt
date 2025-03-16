package com.tsci.core.alert

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.tsci.core.BaseDialogFragment
import com.tsci.core.R
import com.tsci.core.databinding.DialogAlertBinding

class AlertDialogFragment : BaseDialogFragment<DialogAlertBinding>() {

    companion object {
        const val REQUEST_KEY_ALERT = "request_key_alert"
        const val KEY_CLICK = "key_click"

        const val CLICK_POSITIVE = "positive"
        const val CLICK_NEGATIVE = "negative"
    }

    override fun isFullScreenDialog(): Boolean = true

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): DialogAlertBinding = DialogAlertBinding.inflate(layoutInflater, parent, false)

    override fun getDestinationId(): Int = R.id.alertDialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            initListeners()
            setViewsWithArguments(it)
        } ?: run {
            Log.e(TAG, "Empty arguments passed to dialog")
            dismissAllowingStateLoss()
        }
    }

    private fun initListeners() {
        binding.buttonNegative.setOnClickListener {
            val negativeButtonClick = Bundle().apply {
                putString(KEY_CLICK, CLICK_NEGATIVE)
            }
            setFragmentResult(REQUEST_KEY_ALERT, negativeButtonClick)
            dismissAllowingStateLoss()
        }
        binding.buttonPositive.setOnClickListener {
            val positiveButtonClick = Bundle().apply {
                putString(KEY_CLICK, CLICK_POSITIVE)
            }
            setFragmentResult(REQUEST_KEY_ALERT, positiveButtonClick)
            dismissAllowingStateLoss()
        }
    }


    private fun setViewsWithArguments(bundle: Bundle) {
        val message = bundle.getString(AlertDialogFactory.Builder.KEY_MESSAGE, "")
        val buttonPositive = bundle.getString(AlertDialogFactory.Builder.KEY_POSITIVE_BUTTON, "")
        val buttonNegative = bundle.getString(AlertDialogFactory.Builder.KEY_NEGATIVE_BUTTON, "")
        val isCancelable = bundle.getBoolean(AlertDialogFactory.Builder.KEY_CANCELABLE, false)

        this.isCancelable = isCancelable
        binding.textViewAlertMessage.text = message
        if (buttonPositive.isNotBlank()) {
            binding.buttonPositive.text = buttonPositive
            binding.buttonPositive.visibility = View.VISIBLE
        } else {
            binding.buttonPositive.visibility = View.GONE
        }
        if (buttonNegative.isNotBlank()) {
            binding.buttonNegative.text = buttonNegative
            binding.buttonNegative.visibility = View.VISIBLE
        } else {
            binding.buttonNegative.visibility = View.GONE
        }
    }
}