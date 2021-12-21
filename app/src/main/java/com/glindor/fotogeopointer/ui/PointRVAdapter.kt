package com.glindor.fotogeopointer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.databinding.ItemPointBinding

class PointRVAdapter(val onClickListener: ((Point) -> Unit)? = null ):RecyclerView.Adapter<PointRVAdapter.ViewHolder>() {

    var points: List<Point> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemPointBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(points[position])

    override fun getItemCount() = points.size

    inner class ViewHolder(private val binding: ItemPointBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(point: Point) = with(binding) {
            tvName.text = point.name
            tvLati.text = point.lati.toString()
            tvDisc.text = point.disc
            tvLongi.text = point.longi.toString()
            itemView.setOnClickListener {
                onClickListener?.invoke(point)
            }
        }

    }
}