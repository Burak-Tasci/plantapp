package com.tsci.onboarding.getstarted

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.tsci.core.BaseFragment
import com.tsci.core.extension.showToastMessage
import com.tsci.onboarding.R
import com.tsci.onboarding.databinding.FragmentGetStartedBinding

class GetStartedFragment : BaseFragment<FragmentGetStartedBinding>() {

    override fun showAboveNavigationBar(): Boolean = false
    override fun showBelowStatusBar(): Boolean = false

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentGetStartedBinding =
        FragmentGetStartedBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAgreementsSpans()
        setTitleSpan()
        initListeners()
    }

    private fun setTitleSpan() {
        val title = SpannableString(getString(R.string.get_started_title))
        val appName = getString(com.tsci.core.R.string.app_name)

        val start = title.indexOf(appName).takeIf { it != -1 } ?: return
        val end = start + appName.length

        val typeface = Typeface.create(
            ResourcesCompat.getFont(
                requireContext(),
                com.tsci.core.R.font.roboto_semibold
            ), Typeface.NORMAL
        )

        title.setSpan(
            StyleSpan(typeface?.style ?: Typeface.DEFAULT.style),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.textViewGetStartedTitle.text = title

    }

    private fun initListeners() {
        binding.buttonGetStartedStart.setOnClickListener {
            val action = GetStartedFragmentDirections.toOnBoardingFragment()
            findNavController().navigate(action)
        }
    }

    private fun setAgreementsSpans() {
        val agreementsSpannable = SpannableString(
            getString(R.string.get_started_terms_of_use_and_privacy_policy)
        )

        val termsOfUseSpan = getString(R.string.get_started_terms_of_use_span)
        val privacyPolicySpan = getString(R.string.get_started_privacy_policy_span)

        agreementsSpannable.setUnderlinedClickableSpan(termsOfUseSpan)
        agreementsSpannable.setUnderlinedClickableSpan(privacyPolicySpan)

        binding.textViewGetStartedTermsOfUseAndPrivacyPolicy.text = agreementsSpannable
        binding.textViewGetStartedTermsOfUseAndPrivacyPolicy.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun SpannableString.setUnderlinedClickableSpan(span: String): SpannableString {
        val start = indexOf(span)
        val end = start + span.length
        setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                showToastMessage(span)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return this
    }

}