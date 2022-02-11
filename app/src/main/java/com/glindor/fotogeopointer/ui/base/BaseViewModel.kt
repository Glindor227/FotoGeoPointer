package com.glindor.fotogeopointer.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.utils.Logger

open class BaseViewModel<T, S > : ViewModel() {

    open var viewStateLiveDate = MutableLiveData<S>()
    open fun getViewState(): LiveData<S>  {
        return viewStateLiveDate
    }

    // подписчик на данные из репозитория.
    open val observer =  Observer<DataResult>{ result ->
        Logger.d(this,"Инициируем BaseViewModel observer $result")
        viewStateLiveDate.value = when (result) {
                is DataResult.Success<*> -> {
                    Logger.d(this, "viewStateLiveDate.value было = ${viewStateLiveDate.value}")
                    BaseViewState(value = result.data as T, error = null) as S
                }
                is DataResult.Error -> {
                    Logger.d(this,"пришла ошибка " + result.error.toString())
                    BaseViewState(value = null, error = result.error) as S
                }
            }
    }
}