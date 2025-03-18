package com.tsci.home

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.tsci.core.ViewModelFragment
import com.tsci.core.extension.launchWhenResumed
import com.tsci.core.util.HorizontalSpacerItemDecoration
import com.tsci.home.databinding.FragmentHomeBinding
import com.tsci.home.model.CategoryUiModel
import com.tsci.home.model.QuestionUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : ViewModelFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun showAboveNavigationBar(): Boolean = true

    override fun showBelowStatusBar(): Boolean = false

    override val viewModel: HomeViewModel by viewModels()

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setPremiumBannerTextColors()
    }

    private fun setPremiumBannerTextColors() {
        binding.layoutBannerPremiumAvailable.apply {
            textViewBannerPremiumTitle.setPremiumBannerTextColors(
                R.color.premium_banner_title_start_color,
                R.color.premium_banner_title_end_color
            )
            textViewBannerPremiumSubtitle.setPremiumBannerTextColors(
                R.color.premium_banner_subtitle_start_color,
                R.color.premium_banner_subtitle_end_color,
            )

        }
    }

    private fun TextView.setPremiumBannerTextColors(
        @ColorRes startColorResource: Int,
        @ColorRes endColorResource: Int
    ) {
        val startColor = ContextCompat.getColor(
            requireContext(), startColorResource
        )
        val endColor = ContextCompat.getColor(
            requireContext(), endColorResource
        )

        val width: Float = paint.measureText(text.toString())

        val textShader: Shader = LinearGradient(
            0f, 0f, width, textSize, intArrayOf(
                startColor,
                endColor
            ), null, Shader.TileMode.CLAMP
        )
        this.paint.shader = textShader
    }

    private fun observeViewModel() {
        launchWhenResumed {
            launch {
                viewModel.questions.collect {
                    setupQuestionsAdapter(it)
                }
            }
            launch {
                viewModel.categories.collect {
                    setupCategoriesAdapter(it)
                }
            }
        }
    }

    private fun setupCategoriesAdapter(categories: List<CategoryUiModel>) {
        binding.recyclerViewHomeCategories.apply {
            adapter = PlantCategoriesAdapter(categories)
        }
    }

    private fun setupQuestionsAdapter(questions: List<QuestionUiModel>) {
        binding.recyclerViewHomeQuestions.apply {
            adapter = PlantQuestionsAdapter(questions)
            addItemDecoration(
                HorizontalSpacerItemDecoration(
                    resources.getDimensionPixelSize(com.tsci.core.R.dimen.screen_padding_10)
                )
            )
        }
    }
}