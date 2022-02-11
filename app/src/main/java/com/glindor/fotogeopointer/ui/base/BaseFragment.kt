package com.glindor.fotogeopointer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.glindor.fotogeopointer.data.error.NotAuthentication
import com.glindor.fotogeopointer.utils.Logger
import com.google.android.material.snackbar.Snackbar

typealias Inflate<T_VB> = (LayoutInflater, ViewGroup?, Boolean) -> T_VB

abstract class BaseFragment <VB: ViewBinding,T, S : BaseViewState<T>>(private val inflate: Inflate<VB>) :Fragment() {
//abstract class BaseFragment <VB: ViewBinding,T, S : BaseViewState<T>>(private val inflate: VB) :Fragment() {
    abstract val viewModel: BaseViewModel<T, S>
    lateinit var root: View

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        root =  binding.root
        return root
    }

    open fun initViewModel() {
        val a1 = viewModel.getViewState()
        Logger.d(this, "Получение ViewState(BaseFragment) $a1 ${a1.value}")

        // подписка на данные из VM для view
        a1.observe(viewLifecycleOwner, {
            Logger.d(this, "observe Получение ViewState(BaseFragment) $a1 ${a1.value}")
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    protected fun setTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

}