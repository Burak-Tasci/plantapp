package com.tsci.plantapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tsci.core.ActivityInteractor
import com.tsci.plantapp.databinding.ActivityMainBinding
import com.tsci.plantapp.util.SystemBarWindowInsetListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ActivityInteractor {

    private lateinit var binding: ActivityMainBinding

    override val statusBarHeightLiveData: MutableLiveData<Int> = MutableLiveData()

    override val navigationBarHeightLiveData: MutableLiveData<Int> = MutableLiveData()

    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavController()
        observeViewModel()
        applyWindowInsetListener()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.finishActivity.collect { finish ->
                        if (finish) {
                            finish()
                        }
                    }
                }
                launch {
                    viewModel.startDestination.collect { startDestination ->
                        setNavigationGraph(startDestination)
                    }
                }
            }
        }
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            binding.fragmentContainerViewMain.id
        ) as NavHostFragment

        navController = navHostFragment.navController
    }

    private fun setNavigationGraph(@IdRes startDestinationId: Int) {
        if (startDestinationId == MainViewModel.INVALID_START_DESTINATION) {
            return
        }
        val graph = navController.navInflater.inflate(R.navigation.nav_graph_app)
        graph.setStartDestination(startDestinationId)
        navController.setGraph(graph, null)
    }

    private fun applyWindowInsetListener() {
        val listener =
            object : SystemBarWindowInsetListener() {
                override fun onSystemBarHeight(
                    statusBarHeight: Int,
                    navigationBarHeight: Int
                ) {
                    if (statusBarHeightLiveData.value != statusBarHeight)
                        statusBarHeightLiveData.value = statusBarHeight
                    if (navigationBarHeightLiveData.value != navigationBarHeight)
                        navigationBarHeightLiveData.value = navigationBarHeight
                }
            }
        window?.decorView
            ?.setOnApplyWindowInsetsListener(listener)
    }
}