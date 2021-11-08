package com.glindor.fotogeopointer.ui.listpoints

import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.ui.base.BaseViewState

class ListViewSate(value: List<Point>? = null, error:Throwable?)
    : BaseViewState<List<Point>?>(value, error)