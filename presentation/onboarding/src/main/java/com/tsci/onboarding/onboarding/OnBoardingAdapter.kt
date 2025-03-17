package com.tsci.onboarding.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingAdapter(
    private val items: List<OnBoardingItem>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = items.size + 1

    override fun createFragment(position: Int): Fragment {
        val item = items.getOrNull(position) ?: run {
            return Fragment()
        }
        return OnBoardingItemFragment.newInstance(
            title = item.title,
            titleSpan = item.titleSpan,
            contentDrawable = item.contentDrawable
        )
    }
}