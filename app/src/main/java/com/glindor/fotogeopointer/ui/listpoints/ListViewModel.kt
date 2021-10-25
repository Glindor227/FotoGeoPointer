package com.glindor.fotogeopointer.ui.listpoints

import com.glindor.fotogeopointer.data.DataRepository
import com.glindor.fotogeopointer.data.provider.TestProvider
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.ui.base.BaseViewModel

class ListViewModel : BaseViewModel<List<Point>?, ListViewSate>() {

    private var repositoryLiveDate= DataRepository.getPoints()
    init {
        repositoryLiveDate.observeForever(observer)
    }

    override fun onCleared() {
        this.repositoryLiveDate.removeObserver(observer)
        super.onCleared()
    }
}