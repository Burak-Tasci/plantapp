package com.tsci.core.alert

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.tsci.core.extension.launchWhenResumed
import javax.inject.Inject

class FragmentAlertHelper @Inject constructor(private val fragment: Fragment) : AlertHelper() {

    override fun whenResumed(block: suspend (Context) -> Unit) {
        fragment.launchWhenResumed {
            block(fragment.requireContext())
        }
    }

    override fun getNavController(): NavController {
        return fragment.findNavController()
    }

    override fun getFragmentManager(): FragmentManager {
        return fragment.parentFragmentManager
    }

    override fun getLifecycleOwner(): LifecycleOwner {
        return fragment.viewLifecycleOwner
    }
}