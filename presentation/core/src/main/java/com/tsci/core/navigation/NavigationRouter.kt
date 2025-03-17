package com.tsci.core.navigation

sealed class NavigationRouter : NavigationUri {

    data object Paywall : NavigationRouter() {
        override val uri: String = "android-app://com.tsci.paywall/nav_graph_paywall/paywallFragment"
    }

}