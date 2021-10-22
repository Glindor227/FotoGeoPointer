package com.glindor.fotogeopointer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.glindor.fotogeopointer.data.entity.Point

object PointsRepository {
    private var points = MutableLiveData<MutableList<Point>?>()

    private var tempPoints : MutableList<Point>?
    init {
        tempPoints = mutableListOf(
                        Point("0","имя1","описание1",1.0f,1.0f),
                        Point("1","имя2","описание2",2.0f,2.0f),
                        Point("2","имя3","описание3",3.0f,3.0f),
                        Point("3","имя4","описание4",4.0f,4.0f),
                        Point("4","имя1","описание1",1.0f,1.0f),
                        Point("4","имя2","описание2",2.0f,2.0f),
                        Point("4","имя3","описание3",3.0f,3.0f),
                        Point("4","имя4","описание4",4.0f,4.0f),
                        Point("4","имя1","описание1",1.0f,1.0f),
                        Point("4","имя2","описание2",2.0f,2.0f),
                        Point("4","имя3","описание3",3.0f,3.0f),
                        Point("4","имя4","описание4",4.0f,4.0f),
                        Point("4","имя1","описание1",1.0f,1.0f),
                        Point("4","имя2","описание2",2.0f,2.0f),
                        Point("4","имя3","описание3",3.0f,3.0f),
                        Point("4","имя4","описание4",4.0f,4.0f),
                        Point("4","имя1","описание1",1.0f,1.0f),
                        Point("4","имя2","описание2",2.0f,2.0f),
                        Point("4","имя3","описание3",3.0f,3.0f),
                        Point("4","имя4","описание4",4.0f,4.0f))
        points.value = tempPoints

    }

    fun addPoint(point:Point) {
        points.value = points.value.replace(newValue = point) { it == point }?.toMutableList()
    }
    fun getPoints(): LiveData<MutableList<Point>?> = points
}

private fun <E> MutableList<E>?.replace(newValue: E, function: (E) -> Boolean): List<E>? {
    return this?.map {
        if (function(it)) newValue else it
    }
}
