package com.glindor.fotogeopointer.ui.splash

import androidx.lifecycle.LiveData
import com.glindor.fotogeopointer.data.DataRepository
import com.glindor.fotogeopointer.data.entity.User
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.ui.base.BaseViewModel
import com.glindor.fotogeopointer.utils.Logger

class SplashViewModel(private val dataRepository: DataRepository):BaseViewModel<User?,SplashViewState>() {
    private var repositoryLiveDate : LiveData<DataResult>? = null
    fun initUser() {
        repositoryLiveDate?.removeObserver(observer)
        Logger.d(this,"SplashViewModel getCurrentUser")
        repositoryLiveDate = dataRepository.getCurrentUser()
        repositoryLiveDate?.observeForever(observer)
    }
/*
    // Подписка на данные из репозитория
    override val observer =  Observer<DataResult>{ result ->
        Logger.d(this, "Observer create (SplashViewModel) $viewStateLiveDate ${viewStateLiveDate.value}")
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
 */
}