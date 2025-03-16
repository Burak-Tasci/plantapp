package com.tsci.plantapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.MutableLiveData
import com.tsci.core.ActivityInteractor
import com.tsci.plantapp.databinding.ActivityMainBinding
import com.tsci.plantapp.util.SystemBarWindowInsetListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ActivityInteractor {

    private lateinit var binding: ActivityMainBinding

    override val statusBarHeightLiveData: MutableLiveData<Int> = MutableLiveData()

    override val navigationBarHeightLiveData: MutableLiveData<Int> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeSystemBars()
        applyWindowInsetListener()
    }

    private fun observeSystemBars() {
        statusBarHeightLiveData.observe(this) { statusBarHeight ->
            if (statusBarHeight != null && binding.viewStatusBar.height < statusBarHeight) {
                binding.viewStatusBar.updateLayoutParams {
                    height = statusBarHeight
                }
            }
        }
        navigationBarHeightLiveData.observe(this) { navigationBarHeight ->
            if (navigationBarHeight != null && binding.viewNavigationBar.height < navigationBarHeight) {
                binding.viewNavigationBar.updateLayoutParams {
                    height = navigationBarHeight
                }
            }
        }
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