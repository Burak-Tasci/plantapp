package com.tsci.core.extension

import android.os.Looper

fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()