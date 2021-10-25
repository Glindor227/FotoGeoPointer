package com.glindor.fotogeopointer.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Point( val id:String,
                  val name:String,
                  val disc:String,
                  val lati:Float,
                  val longi:Float):Parcelable{

    override fun equals(other: Any?): Boolean = when{
        (other === this) -> true
        (javaClass != other?.javaClass) -> false
        ((other as Point).id == id) -> true
        else ->false
    }

}