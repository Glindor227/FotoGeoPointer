package com.glindor.fotogeopointer.ui.point

import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.ui.base.BaseViewState

class PointViewState(value: Data = Data(), error:Throwable? = null)
    : BaseViewState<PointViewState.Data>(value,error){
        class Data(val data:Point? = null, val isDeleted: Boolean = false)
    }
