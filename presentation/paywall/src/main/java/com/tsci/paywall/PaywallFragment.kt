package com.tsci.paywall

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.tsci.core.ViewModelFragment
import com.tsci.core.extension.launchWhenResumed
import com.tsci.core.extension.showToastMessage
import com.tsci.core.navigation.NavigationRouter
import com.tsci.core.util.HorizontalSpacerItemDecoration
import com.tsci.core.util.StringResource
import com.tsci.core.util.getText
import com.tsci.paywall.databinding.FragmentPaywallBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PaywallFragment : ViewModelFragment<FragmentPaywallBinding, PaywallViewModel>() {

    override fun showAboveNavigationBar(): Boolean = false
    override fun showBelowStatusBar(): Boolean = false

    override val viewModel: PaywallViewModel by viewModels()

    private val featuresAdapter by lazy {
        PaywallFeaturesAdapter(
            features = listOf(
                PaywallFeatureItem(
                    icon = R.drawable.ic_feature_identity,
                    title = R.string.paywall_feature_unlimited_title,
                    subtitle = R.string.paywall_feature_unlimited_description
                ),
                PaywallFeatureItem(
                    icon = R.drawable.ic_feature_fast,
                    title = R.string.paywall_feature_faster_title,
                    subtitle = R.string.paywall_feature_faster_description
                ),
                PaywallFeatureItem(
                    icon = R.drawable.ic_feature_healthy_flower,
                    title = R.string.paywall_feature_healthy_title,
                    subtitle = R.string.paywall_feature_healthy_description
                )
            )
        )
    }

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentPaywallBinding = FragmentPaywallBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubscriptionFeatures()
        setupTitleBoldSpan()
        initListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        launchWhenResumed {
            launch {
                viewModel.subscriptionState.collect {
                    setSubscriptionState(it)
                }
            }
            launch {
                viewModel.subscriptionSuccess.collect {
                    handleSubscriptionSuccess(it)
                }
            }
            launch {
                viewModel.navigation.collect { navigation ->
                    when (navigation) {
                        PaywallNavigation.Home -> {
                            val request = NavDeepLinkRequest.Builder
                                .fromUri(NavigationRouter.Host.uri)
                                .build()
                            findNavController().navigate(request)

                        }

                        PaywallNavigation.Pop -> {
                            findNavController().navigateUp()
                        }

                        PaywallNavigation.None -> Unit
                    }
                }
            }
        }
    }

    private fun handleSubscriptionSuccess(stringResource: StringResource) {
        val message = stringResource.getText(requireContext())
        if (message.isNotBlank()) {
            alertHelper.showAlert(
                message = message,
                positiveButton = getString(com.tsci.core.R.string.general_term_ok)
            )
        }
    }

    private fun setSubscriptionState(state: SubscriptionState) {
        when (state) {
            SubscriptionState.Monthly -> {
                binding.constraintLayoutPaywallSubscriptionOneMonthContainer.isSelected = true
                binding.radioButtonPaywallSubscriptionOneMonth.isChecked = true
                binding.constraintLayoutPaywallSubscriptionOneYearContainer.isSelected = false
                binding.radioButtonPaywallSubscriptionOneYear.isChecked = false
            }

            SubscriptionState.None -> {
                binding.constraintLayoutPaywallSubscriptionOneMonthContainer.isSelected = false
                binding.radioButtonPaywallSubscriptionOneMonth.isChecked = false
                binding.constraintLayoutPaywallSubscriptionOneYearContainer.isSelected = false
                binding.radioButtonPaywallSubscriptionOneYear.isChecked = false
            }

            SubscriptionState.Yearly -> {
                binding.constraintLayoutPaywallSubscriptionOneMonthContainer.isSelected = false
                binding.radioButtonPaywallSubscriptionOneMonth.isChecked = false
                binding.constraintLayoutPaywallSubscriptionOneYearContainer.isSelected = true
                binding.radioButtonPaywallSubscriptionOneYear.isChecked = true
            }
        }
    }

    private fun setupTitleBoldSpan() {
        val title = getString(R.string.paywall_title)
        val titleSpan = getString(com.tsci.core.R.string.app_name)

        val spannableTitle = SpannableString(title)
        val titleSpanStart = title.indexOf(titleSpan)
        val titleSpanEnd = titleSpanStart + titleSpan.length

        val typeface = Typeface.create(
            ResourcesCompat.getFont(
                requireContext(),
                com.tsci.core.R.font.roboto_semibold
            ), Typeface.BOLD
        )

        spannableTitle.setSpan(
            android.text.style.StyleSpan(typeface.style),
            titleSpanStart,
            titleSpanEnd,
            android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        binding.textViewPaywallTitle.text = spannableTitle
    }

    private fun setupSubscriptionFeatures() {

        val spacing8dp = resources.getDimensionPixelSize(com.tsci.core.R.dimen.screen_padding_8)

        binding.recyclerViewPaywallSubscriptionFeatures.apply {
            adapter = featuresAdapter
            addItemDecoration(
                HorizontalSpacerItemDecoration(spacing8dp)
            )
        }
    }

    private fun initListeners() {

        binding.imageViewPaywallClose.setOnClickListener {
            viewModel.closePaywall()
        }

        binding.constraintLayoutPaywallSubscriptionOneMonthContainer.setOnClickListener {
            viewModel.selectMonthlySubscription()
        }

        binding.constraintLayoutPaywallSubscriptionOneYearContainer.setOnClickListener {
            viewModel.selectYearlySubscription()
        }

        binding.buttonPaywallTryFree.setOnClickListener {
            viewModel.selectSubscription()
        }

        /**
         * Normally these buttons should navigate to agreement screens but, in this case
         * we don't have one. So just showing a toast message.
         */
        binding.textViewPaywallAgreementTerms.setOnClickListener {
            showToastMessage(getString(R.string.paywall_agreement_terms))
        }
        binding.textViewPaywallAgreementPrivacy.setOnClickListener {
            showToastMessage(getString(R.string.paywall_agreement_privacy))
        }
        binding.textViewPaywallAgreementRestore.setOnClickListener {
            showToastMessage(getString(R.string.paywall_agreement_restore))
        }
    }

}