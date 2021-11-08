package com.glindor.fotogeopointer.ui.base

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.data.error.NotAuthentication
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment <T, S : BaseViewState<T>> :Fragment() {
    abstract val viewModel: BaseViewModel<T, S>
    lateinit var root: View
    open fun initViewModel() {
        viewModel.getViewState().observe(viewLifecycleOwner, {
            it.error?.let { error ->
                renderError(error)
            }
            it.value?.let { newList ->
                renderData(newList)
            }
        })
    }

    abstract fun renderData(newData: T)
    open fun renderError(error: Throwable){
        when(error){
            is NotAuthentication -> {
                Snackbar.make(root, "Ошибка : NotAuthentication", Snackbar.LENGTH_LONG).show()
//                StartAuthentication()
            }
            else -> Snackbar.make(root, "Ошибка :${error}", Snackbar.LENGTH_LONG).show()
        }

    }

}