package com.glindor.fotogeopointer.data

import androidx.lifecycle.LiveData
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.data.provider.FireStoreDataBaseProvider
import com.glindor.fotogeopointer.data.provider.IDataProvider

object DataRepository {
    private val currentRepository: IDataProvider = FireStoreDataBaseProvider()
    fun addPoint(point: Point) = currentRepository.addPoint(point)
    fun getPoints() : LiveData<DataResult> = currentRepository.getPoints()
    fun getPoint(id:String) : LiveData<DataResult> = currentRepository.getPoint(id)
    fun getCurrentUser() = currentRepository.getCurrentUser()
}