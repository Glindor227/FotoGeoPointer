package com.glindor.fotogeopointer.ui.point

import androidx.lifecycle.LiveData
import com.glindor.fotogeopointer.data.DataRepository
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.ui.base.BaseViewModel
import com.glindor.fotogeopointer.utils.Logger


class PointViewModel: BaseViewModel<Point?, PointViewState>() {
    private var repositoryLiveDate :LiveData<DataResult>? = null
    private var storagePoint: Point? = null
    fun savePoint(point: Point) {
        storagePoint = point
        Logger.d(this,"Записываем $point")
        storagePoint?.let {
            DataRepository.addPoint(it) // по идее тут надо ловить LiveDate с ошибкой
        }
    }
    fun loadPoint(id: String) {
        repositoryLiveDate?.removeObserver(observer)
        repositoryLiveDate = DataRepository.getPoint(id)
        repositoryLiveDate?.observeForever(observer)
    }
    override fun onCleared() {
        storagePoint?.let { DataRepository.addPoint(it) }
        repositoryLiveDate?.removeObserver(observer)
        super.onCleared()
    }
}