package com.glindor.fotogeopointer.ui.listpoints

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glindor.fotogeopointer.data.PointsRepository

class ListViewModel : ViewModel() {

    private var viewStateLiveDate: MutableLiveData<ListViewSate> = MutableLiveData()
    init {
        PointsRepository.getPoints().observeForever {
            viewStateLiveDate.value = viewStateLiveDate.value?.copy(points = it) ?: ListViewSate(it)
        }
    }

    //    val viewState: LiveData<DashboardViewSate> = viewStateLiveDate
    fun getViewSate():  LiveData<ListViewSate> = viewStateLiveDate
}