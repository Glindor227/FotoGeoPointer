package com.glindor.fotogeopointer.data.provider

import androidx.lifecycle.LiveData
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.data.entity.User
import com.glindor.fotogeopointer.data.model.DataResult

interface IDataProvider {
    fun getPoints(): LiveData<DataResult>
    fun getPoint(id:String) : LiveData<DataResult>
    fun addPoint(point:Point) : LiveData<DataResult>
    fun getCurrentUser() : LiveData<DataResult>
}