package com.glindor.fotogeopointer.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.glindor.fotogeopointer.data.DataRepository
import com.glindor.fotogeopointer.data.entity.User
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.ui.base.BaseViewModel
import com.glindor.fotogeopointer.ui.base.BaseViewState
import com.glindor.fotogeopointer.utils.Logger

class SplashViewModel:BaseViewModel<User?,SplashViewState>() {
    private var repositoryLiveDate : LiveData<DataResult>? = null
    fun initUser() {
        repositoryLiveDate?.removeObserver(observer)
        repositoryLiveDate = DataRepository.getCurrentUser()
        repositoryLiveDate?.observeForever(observer)
    }


    override val observer =  Observer<DataResult>{ result ->
        when (result) {
            is DataResult.Success<*> -> {
                Logger.d(this,"пришли данные "+ result.data.toString())
                viewStateLiveDate.value = SplashViewState(value = result.data as User,null)
            }
            is DataResult.Error -> {
                Logger.d(this,"пришла ошибка " + result.error.toString())
                viewStateLiveDate.value = SplashViewState(value = null, error = result.error)
            }
        }
    }
}