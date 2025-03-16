package com.tsci.core.alert

import android.os.Bundle

enum class AlertResult {
    POSITIVE_BUTTON_CLICK,
    NEGATIVE_BUTTON_CLICK,
    CANCELED;
}

fun Bundle.toAlertResult(): AlertResult {
    return getString(AlertDialogFragment.KEY_CLICK)?.let {
        when (it) {
            AlertDialogFragment.CLICK_POSITIVE -> AlertResult.POSITIVE_BUTTON_CLICK
            AlertDialogFragment.CLICK_NEGATIVE -> AlertResult.NEGATIVE_BUTTON_CLICK
            else -> AlertResult.CANCELED
        }
    }
        ?: throw NullPointerException("Missing argument in bundle: ${AlertDialogFragment.KEY_CLICK}, $this")
}