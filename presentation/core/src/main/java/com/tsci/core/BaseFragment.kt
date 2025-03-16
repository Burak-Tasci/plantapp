package com.tsci.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {


    abstract fun bindingInflater(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): VB

    /**
     * The binding property is only valid between onCreateView and
     * onDestroyView
     */
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    // The status bar height live data, bound to main activity.
    // It will not fire if showBelowStatusBar() is true.
    protected val statusBarHeightLiveData: LiveData<Int>? by lazy {
        if (!showBelowStatusBar())
            (activity as? ActivityInteractor)?.statusBarHeightLiveData
        else
            MutableLiveData()
    }

    // The navigation bar height live data, bound to main activity.
    // It will not fire if showAboveNavigationBar() is true.
    protected val navigationBarHeightLiveData: LiveData<Int>? by lazy {
        if (!showAboveNavigationBar())
            (activity as? ActivityInteractor)?.navigationBarHeightLiveData
        else
            MutableLiveData()
    }

    // The method that allows you to adjust
    // the root view based on the status bar
    // height. If the method returns true,
    // the content will be pushed down to
    // (a.k.a top padding will be added)
    // below the status bar. Otherwise,
    // if the status bar height is required
    // for customized usage, then
    // this method should return false.
    protected open fun showAboveNavigationBar() = true

    // The method that allows you to adjust
    // the root view based on the status bar
    // height. If the method returns true,
    // the content will be pushed down to
    // (a.k.a top padding will be added)
    // below the status bar. Otherwise,
    // if the status bar height is required
    // for customized usage, then
    // this method should return false.
    protected open fun showBelowStatusBar() = true

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (showBelowStatusBar()) {
            (activity as? ActivityInteractor)
                ?.statusBarHeightLiveData?.observe(viewLifecycleOwner) { statusBarHeight ->
                    if (view.paddingTop < statusBarHeight) {
                        view.updatePadding(
                            top = statusBarHeight
                        )
                    }
                }
        }
        if (showAboveNavigationBar()) {
            (activity as? ActivityInteractor)
                ?.navigationBarHeightLiveData?.observe(viewLifecycleOwner) { navigationBarHeight ->
                    if (view.paddingBottom < navigationBarHeight) {
                        view.updatePadding(
                            bottom = navigationBarHeight
                        )
                    }
                }
        }
    }
}