package com.tsci.paywall

sealed interface PaywallNavigation {

    data object None : PaywallNavigation

    data object Pop : PaywallNavigation

    data object Home: PaywallNavigation

}