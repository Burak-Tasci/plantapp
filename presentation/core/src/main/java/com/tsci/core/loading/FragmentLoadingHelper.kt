package com.tsci.core.loading

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.tsci.core.extension.launchWhenResumed
import javax.inject.Inject

class FragmentLoadingHelper @Inject constructor(private val fragment: Fragment): LoadingHelper() {

    override fun whenResumed(block: suspend (NavController) -> Unit) {
        fragment.launchWhenResumed {
            block(fragment.findNavController())
        }
    }
}