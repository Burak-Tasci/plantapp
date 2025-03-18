package com.tsci.core.navigation

import android.net.Uri
import androidx.core.net.toUri

sealed class NavigationRouter : NavigationUri {

    data object Paywall : NavigationRouter() {
        override val uri: Uri = "android-app://com.tsci.paywall/nav_graph_paywall/paywallFragment".toUri()
    }

    data object Host: NavigationRouter() {
        override val uri: Uri = "android-app://com.tsci.host/nav_graph_host_container/hostFragment".toUri()

    }

}