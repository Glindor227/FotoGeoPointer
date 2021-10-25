package com.glindor.fotogeopointer.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment <T, S : BaseViewState<T>> : Fragment() {
    abstract val viewModel: BaseViewModel<T, S>

    open fun initViewModel() {
        viewModel.getViewState().observe(viewLifecycleOwner, {
            it?.let {
                it.value?.let { newList ->
                    renderData(newList)
                }
            }
        })
    }

    abstract fun renderData(newData: T)
}