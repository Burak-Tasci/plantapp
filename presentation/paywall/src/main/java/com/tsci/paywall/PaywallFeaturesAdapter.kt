package com.tsci.paywall

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsci.paywall.databinding.ItemPaywallFeatureBinding

class PaywallFeaturesAdapter(
    private val features: List<PaywallFeatureItem>
) : RecyclerView.Adapter<PaywallFeaturesAdapter.PaywallFeatureViewHolder>() {

    inner class PaywallFeatureViewHolder(
        private val binding: ItemPaywallFeatureBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PaywallFeatureItem) {
            val context = binding.root.context
            binding.textViewPaywallFeatureTitle.text = context.getString(item.title)
            binding.textViewPaywallFeatureSubtitle.text = context.getString(item.subtitle)
            binding.imageViewPaywallFeatureIcon.setImageResource(item.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaywallFeatureViewHolder {
        val binding = ItemPaywallFeatureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaywallFeatureViewHolder(binding)
    }

    override fun getItemCount(): Int = features.size

    override fun onBindViewHolder(holder: PaywallFeatureViewHolder, position: Int) {
        val item = features[position]
        holder.bind(item)
    }

}