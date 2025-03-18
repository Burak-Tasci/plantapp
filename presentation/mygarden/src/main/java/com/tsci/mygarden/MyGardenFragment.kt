package com.tsci.mygarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsci.core.BaseFragment
import com.tsci.mygarden.databinding.FragmentMyGardenBinding

class MyGardenFragment : BaseFragment<FragmentMyGardenBinding>() {
    override fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentMyGardenBinding = FragmentMyGardenBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewMyGarden.text = this::class.java.simpleName
    }
}