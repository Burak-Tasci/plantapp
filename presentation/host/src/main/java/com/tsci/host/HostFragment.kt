package com.tsci.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tsci.core.BaseFragment
import com.tsci.main.databinding.FragmentHostBinding

class HostFragment : BaseFragment<FragmentHostBinding>() {

    override fun showAboveNavigationBar(): Boolean = false

    override fun showBelowStatusBar(): Boolean = false

    private lateinit var navController: NavController

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHostBinding = FragmentHostBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment = childFragmentManager.findFragmentById(
            binding.fragmentContainerViewHost.id
        ) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigationMenu.setupWithNavController(navController)
    }


}