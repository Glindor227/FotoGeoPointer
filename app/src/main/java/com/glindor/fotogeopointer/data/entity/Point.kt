package com.glindor.fotogeopointer.data.entity

data class Point(var id:String = "",
                 val name:String = "",
                 val disc:String = "",
                 val lati:Float = 0.0f,
                 val longi:Float = 0.0f) {

    override fun equals(other: Any?): Boolean = when{
        (other === this) -> true
        (javaClass != other?.javaClass) -> false
        ((other as Point).id == id) -> true
        else ->false
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + disc.hashCode()
        result = 31 * result + lati.hashCode()
        result = 31 * result + longi.hashCode()
        return result
    }

}