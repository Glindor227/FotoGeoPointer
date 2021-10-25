package com.glindor.fotogeopointer.data

import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.data.provider.IDataProvider
import com.glindor.fotogeopointer.data.provider.TestProvider

object DataRepository {
    private val currentRepository: IDataProvider = TestProvider()
    fun addPoint(point: Point) = currentRepository.addPoint(point)
    fun getPoints() = currentRepository.getPoints()
    fun getPoint(id:String) = currentRepository.getPoint(id)
}