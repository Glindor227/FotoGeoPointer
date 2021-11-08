package com.glindor.fotogeopointer.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.utils.Logger

open class BaseViewModel<T, S : BaseViewState<T>> : ViewModel() {

    open var viewStateLiveDate = MutableLiveData<S>()
    open fun getViewState(): LiveData<S>  {
        return viewStateLiveDate
    }
    open val observer =  Observer<DataResult>{ result ->
            when (result) {
                is DataResult.Success<*> -> {
                    Logger.d(this,"пришли данные "+ result.data.toString())
                    viewStateLiveDate.value = BaseViewState(value = result.data as T,error = null) as S
                }
                is DataResult.Error -> {
                    Logger.d(this,"пришла ошибка " + result.error.toString())
                    viewStateLiveDate.value = BaseViewState(value = null, error = result.error) as S
                }
            }
    }
}