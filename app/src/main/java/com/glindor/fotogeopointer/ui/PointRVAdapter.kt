package com.glindor.fotogeopointer.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.data.entity.Point
import kotlinx.android.synthetic.main.item_point.view.*

class PointRVAdapter(val onClickListener: ((Point) -> Unit)? = null ):RecyclerView.Adapter<PointRVAdapter.ViewHolder>() {

    var points: List<Point> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_point,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(points[position])

    override fun getItemCount() = points.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        fun bind(point: Point) = with(itemView) {
            tv_name.text = point.name
            tv_lati.text = point.lati.toString()
            tv_disc.text = point.disc
            tv_longi.text = point.longi.toString()
            itemView.setOnClickListener {
                onClickListener?.invoke(point)
            }
        }

    }
}