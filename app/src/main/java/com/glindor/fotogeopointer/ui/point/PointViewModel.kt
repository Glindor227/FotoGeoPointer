package com.glindor.fotogeopointer.ui.point

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.glindor.fotogeopointer.data.DataRepository
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.ui.base.BaseViewModel
import com.glindor.fotogeopointer.ui.base.BaseViewState
import com.glindor.fotogeopointer.utils.Logger


class PointViewModel(private val dataRepository: DataRepository): BaseViewModel<PointViewState.Data, PointViewState>() {
    private var repositoryLiveDate :LiveData<DataResult>? = null
    private val storagePoint: Point?
        get() = viewStateLiveDate.value?.value?.data

    fun savePoint(point: Point) {
        Logger.d(this,"Запоминаем $point (storagePoint = $storagePoint)")
        storagePoint?.let {
            point.id = it.id
        }
        dataRepository.addPoint(point).observeForever(observer)
    }

    fun loadPoint(id: String) {
        repositoryLiveDate?.removeObserver(observer)
        repositoryLiveDate = dataRepository.getPoint(id)
        repositoryLiveDate?.observeForever(observer)
    }

    fun deletePoint() {
        Logger.d(this, "Удаляем $storagePoint")
        storagePoint?.let {
            dataRepository.deletePoint(it.id).observeForever { deleteResult ->
                viewStateLiveDate.value = when (deleteResult) {
                    is DataResult.Success<*> ->
                        PointViewState(value = PointViewState.Data(isDeleted = true))
                    is DataResult.Error -> PointViewState(error = deleteResult.error)
                }
            }
        }
    }

    override fun onCleared() {
        repositoryLiveDate?.removeObserver(observer)
        super.onCleared()
    }

    override val observer =  Observer<DataResult>{ result ->
        Logger.d(this,"Инициируем PointViewModel observer")
        result?.let {
            viewStateLiveDate.value = when (result) {
                is DataResult.Success<*> ->
                    PointViewState(value = PointViewState.Data(data = result.data as Point))
                is DataResult.Error -> PointViewState(error = result.error)
            }
        }
    }
}