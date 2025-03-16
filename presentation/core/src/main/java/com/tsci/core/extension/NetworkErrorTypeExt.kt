package com.tsci.core.extension

import android.content.Context
import com.tsci.core.R
import com.tsci.domain.NetworkErrorType


fun NetworkErrorType?.toErrorMessage(context: Context): String {
    val resource = when (this) {
        NetworkErrorType.SERIALIZATION, NetworkErrorType.TOO_MANY_REQUESTS -> {
            R.string.error_message_invalid_request_response
        }

        NetworkErrorType.REQUEST_TIMEOUT -> {
            R.string.error_message_request_timeout
        }

        else -> {
            R.string.error_message_an_error_occurred
        }
    }
    return context.getString(resource)
}