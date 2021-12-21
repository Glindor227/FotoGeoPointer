package com.glindor.fotogeopointer.data

import androidx.lifecycle.LiveData
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.data.provider.FireBaseProvider
import com.glindor.fotogeopointer.data.provider.IDataProvider

class DataRepository(private val currentRepository: IDataProvider) {
    fun addPoint(point: Point) = currentRepository.addPoint(point)
    fun getPoints() : LiveData<DataResult> = currentRepository.getPoints()
    fun getPoint(id:String) : LiveData<DataResult> = currentRepository.getPoint(id)
    fun getCurrentUser() = currentRepository.getCurrentUser()
}