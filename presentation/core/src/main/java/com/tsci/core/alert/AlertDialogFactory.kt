package com.tsci.core.alert

import android.os.Bundle

class AlertDialogFactory {


    class Builder {

        companion object {
            const val KEY_MESSAGE = "message"
            const val KEY_POSITIVE_BUTTON = "positive_button"
            const val KEY_NEGATIVE_BUTTON = " negative_button"
            const val KEY_CANCELABLE = "cancelable"
        }

        private var message: String = ""
        private var positiveButton: String = ""
        private var negativeButton: String = ""
        private var cancelable: Boolean = false


        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setPositiveButton(text: String): Builder {
            this.positiveButton = text
            return this
        }

        fun setNegativeButton(text: String): Builder {
            this.negativeButton = text
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun build(): Bundle {
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            bundle.putString(KEY_POSITIVE_BUTTON, positiveButton)
            bundle.putString(KEY_NEGATIVE_BUTTON, negativeButton)
            bundle.putBoolean(KEY_CANCELABLE, cancelable)
            return bundle
        }
    }

}