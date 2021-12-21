package com.glindor.fotogeopointer.ui.listpoints

import androidx.lifecycle.LiveData
import com.glindor.fotogeopointer.data.DataRepository
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.ui.base.BaseViewModel

class ListViewModel(private val dataRepository: DataRepository) : BaseViewModel<List<Point>?, ListViewSate>() {

    private var repositoryLiveDate : LiveData<DataResult> = dataRepository.getPoints()

    init {
        repositoryLiveDate.observeForever(observer)
    }

    override fun onCleared() {
        this.repositoryLiveDate.removeObserver(observer)
        super.onCleared()
    }
}