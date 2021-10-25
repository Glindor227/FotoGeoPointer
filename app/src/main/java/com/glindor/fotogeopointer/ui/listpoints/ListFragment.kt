package com.glindor.fotogeopointer.ui.listpoints

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.ui.PointRVAdapter
import com.glindor.fotogeopointer.ui.base.BaseFragment
import com.glindor.fotogeopointer.ui.point.PointFragment
import com.glindor.fotogeopointer.utils.Logger
import kotlinx.android.synthetic.main.fragment_first.view.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : BaseFragment<List<Point>?, ListViewSate>() {

    override val viewModel: ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }
    private lateinit var adapter: PointRVAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_first, container, false)
        initViewModel()
        initUserView(root)
        return  root
    }

    private fun initUserView(fragment: View){
        val recyclerViewManager = GridLayoutManager(requireContext(),2)
        fragment.rv_points.layoutManager = recyclerViewManager
        adapter = PointRVAdapter(onClickListener = {
            PointFragment.start(this,it)
        })
        fragment.rv_points.adapter = adapter
        Logger.d("1")
        fragment.fab.setOnClickListener {
            Logger.d("2")
            PointFragment.start(this)
        }
        Logger.d("3")

    }

    override fun renderData(newData: List<Point>?) {
        newData?.let {
            adapter.points = it
        }
    }
}