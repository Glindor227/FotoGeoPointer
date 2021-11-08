package com.glindor.fotogeopointer.ui.point

import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.ui.base.BaseViewState

class PointViewState(value: Point? = null, error:Throwable?)
    : BaseViewState<Point?>(value,error)
