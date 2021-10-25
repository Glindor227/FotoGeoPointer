package com.glindor.fotogeopointer.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.utils.Logger

class TestProvider: IDataProvider {
    private var points = MutableLiveData<MutableList<Point>?>()
    private var point = MutableLiveData<Point?>()

    private var tempPoints: MutableList<Point>? = mutableListOf(
        Point("0", "имя1", "описание1", 1.0f, 1.0f),
        Point("1", "имя2", "описание2", 2.0f, 2.0f),
        Point("2", "имя3", "описание3", 3.0f, 3.0f),
        Point("3", "имя4", "описание4", 4.0f, 4.0f),
        Point("4", "имя1", "описание1", 1.0f, 1.0f),
        Point("5", "имя2", "описание2", 2.0f, 2.0f),
        Point("6", "имя3", "описание3", 3.0f, 3.0f),
        Point("7", "имя4", "описание4", 4.0f, 4.0f),
        Point("8", "имя1", "описание1", 1.0f, 1.0f),
        Point("9", "имя2", "описание2", 2.0f, 2.0f),
        Point("10", "имя3", "описание3", 3.0f, 3.0f),
        Point("11", "имя4", "описание4", 4.0f, 4.0f),
        Point("12", "имя1", "описание1", 1.0f, 1.0f),
        Point("13", "имя2", "описание2", 2.0f, 2.0f),
        Point("14", "имя3", "описание3", 3.0f, 3.0f),
        Point("15", "имя4", "описание4", 4.0f, 4.0f),
        Point("16", "имя1", "описание1", 1.0f, 1.0f),
        Point("17", "имя2", "описание2", 2.0f, 2.0f),
        Point("18", "имя3", "описание3", 3.0f, 3.0f),
        Point("19", "имя4", "описание4", 4.0f, 4.0f)
    )

    init {
        points.value = tempPoints

    }

    override fun addPoint(point: Point) {
        points.value?.find { it == point }?.let {
            Logger.d("заменяем $point")
            points.value = points.value.replace(newValue = point) { it == point }?.toMutableList()
        } ?: let {
            Logger.d("добавляем $point")
            points.value?.add(point)
        }
    }

    override fun getPoints(): LiveData<MutableList<Point>?> = points
    override fun getPoint(id: String): LiveData<Point?> {
        point.value = points.value?.find { it.id == id }
        return point
    }

    private fun <E> MutableList<E>?.replace(newValue: E, function: (E) -> Boolean): List<E>? {
        return this?.map {
            if (function(it)) newValue else it
        }
    }
}
