package com.glindor.fotogeopointer.data.provider

import androidx.lifecycle.LiveData
import com.glindor.fotogeopointer.data.entity.Point

interface IDataProvider {
    fun getPoints(): LiveData<MutableList<Point>?>
    fun getPoint(id:String) : LiveData<Point?>
    fun addPoint(point:Point)
}