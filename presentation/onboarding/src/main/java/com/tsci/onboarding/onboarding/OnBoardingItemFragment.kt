package com.tsci.onboarding.onboarding

import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.tsci.core.BaseFragment
import com.tsci.onboarding.R
import com.tsci.onboarding.databinding.FragmentOnBoardingItemBinding


class OnBoardingItemFragment : BaseFragment<FragmentOnBoardingItemBinding>() {


    override fun showAboveNavigationBar(): Boolean = false
    override fun showBelowStatusBar(): Boolean = false

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentOnBoardingItemBinding =
        FragmentOnBoardingItemBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val args = arguments ?: run {
            return
        }

        val title: String = args.getString(KEY_TITLE, "")
        val titleSpan: String = args.getString(KEY_TITLE_SPAN, "")
        val contentDrawable = args.getInt(KEY_CONTENT_DRAWABLE, 0)

        val spannableTitle = SpannableString(title)
        val titleSpanStart = title.indexOf(titleSpan)
        val titleSpanEnd = titleSpanStart + titleSpan.length

        val typeface = Typeface.create(
            ResourcesCompat.getFont(
                requireContext(),
                com.tsci.core.R.font.roboto
            ), Typeface.BOLD
        )

        spannableTitle.setSpan(
            android.text.style.StyleSpan(typeface.style),
            titleSpanStart,
            titleSpanEnd,
            android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        binding.textViewOnBoardingItemTitle.text = spannableTitle
        val coordinates = getWordCoordinates(binding.textViewOnBoardingItemTitle, titleSpan)
        val brushView = AppCompatImageView(requireContext()).apply {
            id = View.generateViewId()
            setImageResource(R.drawable.img_brush_small)
            x = coordinates.first
            y = coordinates.second
        }
        binding.root.addView(brushView)



        if (contentDrawable != 0) {
            binding.imageViewOnBoardingItemContent.setImageResource(contentDrawable)
        }
    }

    private fun getWordCoordinates(textView: TextView, word: String): Pair<Float, Float> {
        val totalString = textView.text.toString()

        val paint = binding.textViewOnBoardingItemTitle.paint
        val bounds = Rect()
        paint.getTextBounds(word, 0, word.length, bounds)

        val texts = paint.measureText(
            totalString.substringBefore(word)
        )
        val x = texts + resources.getDimensionPixelOffset(com.tsci.core.R.dimen.screen_padding_20)
        val y = bounds.height().toFloat() + resources.getDimensionPixelOffset(com.tsci.core.R.dimen.screen_padding_48)


        return Pair(x, y)
    }


    companion object {

        private const val KEY_CONTENT_DRAWABLE = "KEY_BACKGROUND_DRAWABLE"
        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_TITLE_SPAN = "KEY_TITLE_SPAN"

        fun newInstance(
            title: String,
            titleSpan: String,
            contentDrawable: Int
        ): OnBoardingItemFragment {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_TITLE_SPAN, titleSpan)
            args.putInt(KEY_CONTENT_DRAWABLE, contentDrawable)
            val fragment = OnBoardingItemFragment()
            fragment.arguments = args
            return fragment
        }
    }
}