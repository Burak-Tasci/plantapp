package com.tsci.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.tsci.core.BaseFragment
import com.tsci.main.R
import com.tsci.main.databinding.FragmentHostBinding

class HostFragment : BaseFragment<FragmentHostBinding>() {

    override fun showAboveNavigationBar(): Boolean = false

    override fun showBelowStatusBar(): Boolean = false

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHostBinding = FragmentHostBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavController()
        setupOnBackPressedCallback()
    }

    private fun setupOnBackPressedCallback() {

        requireActivity().onBackPressedDispatcher.addCallback(owner = this) {
            val navigateUp = navController.navigateUp(appBarConfiguration)
            if (!navigateUp) {
                activity?.finish()
            }
        }
    }

    private fun setupNavController() {

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_graph_home,
                R.id.nav_graph_diagnose,
                R.id.nav_graph_mygarden,
                R.id.nav_graph_profile,
            )
        )

        val navHostFragment = childFragmentManager.findFragmentById(
            binding.fragmentContainerViewHost.id
        ) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigationMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isVisible = appBarConfiguration.isTopLevelDestination(destination)
            binding.groupBottomNavigationBar.isVisible = isVisible
        }

    }


}