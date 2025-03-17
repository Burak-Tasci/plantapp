package com.tsci.onboarding.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.tsci.core.BaseFragment
import com.tsci.onboarding.R
import com.tsci.onboarding.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {

    override fun showAboveNavigationBar(): Boolean = false

    override fun showBelowStatusBar(): Boolean = false

    private val onBoardingAdapter by lazy {
        OnBoardingAdapter(
            items = listOf(
                OnBoardingItem(
                    title = getString(R.string.onboarding_item_take_a_photo),
                    titleSpan = getString(R.string.onboarding_item_identify),
                    contentDrawable = R.drawable.img_phone_scanning_plant,
                    brushDrawable = R.drawable.img_brush_small
                ),
                OnBoardingItem(
                    title = getString(R.string.onboarding_item_get_plant_care),
                    titleSpan = getString(R.string.onboarding_item_care_guides),
                    contentDrawable = R.drawable.img_phone_plant_overview,
                    brushDrawable = R.drawable.img_brush_medium
                ),
            ),
            fragment = this
        )
    }

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentOnBoardingBinding =
        FragmentOnBoardingBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonOnBoardingContinue.setOnClickListener {
            val position = binding.tabLayoutDotsIndicator.selectedTabPosition

            when(position) {
                0 -> {
                    binding.viewPagerOnBoardingContent.setCurrentItem(1, true)
                }
                1 -> {
                    // todo navigate paywall
                }
                else -> {
                    return@setOnClickListener
                }
            }

        }
    }

    private fun initViewPager() {
        binding.viewPagerOnBoardingContent.adapter = onBoardingAdapter
        binding.viewPagerOnBoardingContent.isUserInputEnabled = false

        TabLayoutMediator(
            binding.tabLayoutDotsIndicator,
            binding.viewPagerOnBoardingContent
        ) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }
}