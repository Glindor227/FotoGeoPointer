package com.glindor.fotogeopointer.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

open class BaseViewModel<T, S : BaseViewState<T>> : ViewModel() {

    open var viewStateLiveDate = MutableLiveData<S>()
    open fun getViewState(): LiveData<S> = viewStateLiveDate
    open val observer =  Observer<T>{
        it?.let { date ->
            viewStateLiveDate.value = BaseViewState<T>(date) as S
        }
    }
}