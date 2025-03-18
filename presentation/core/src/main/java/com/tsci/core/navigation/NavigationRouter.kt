package com.tsci.core.navigation

import android.net.Uri
import androidx.core.net.toUri

sealed class NavigationRouter : NavigationUri {

    data object Paywall : NavigationRouter() {
        override val uri: Uri = "android-app://com.tsci.paywall/nav_graph_paywall/paywallFragment".toUri()
    }

    data object Home: NavigationRouter() {
        override val uri: Uri = "android-app://com.tsci.home/nav_graph_home/homeFragment".toUri()

    }

}