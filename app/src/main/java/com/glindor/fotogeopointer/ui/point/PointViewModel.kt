package com.glindor.fotogeopointer.ui.point

import androidx.lifecycle.ViewModel
import com.glindor.fotogeopointer.data.PointsRepository
import com.glindor.fotogeopointer.data.entity.Point

class PointViewModel: ViewModel() {
    private var storagePoint: Point? = null
    fun savePoint(point: Point){
        storagePoint = point
    }

    override fun onCleared() {
        storagePoint?.let { PointsRepository.addPoint(it) }
    }
}