package com.tsci.diagnose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsci.core.BaseFragment
import com.tsci.diagnose.databinding.FragmentDiagnoseBinding

class DiagnoseFragment : BaseFragment<FragmentDiagnoseBinding>() {

    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentDiagnoseBinding = FragmentDiagnoseBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewDiagnose.text = this::class.java.simpleName
    }
}