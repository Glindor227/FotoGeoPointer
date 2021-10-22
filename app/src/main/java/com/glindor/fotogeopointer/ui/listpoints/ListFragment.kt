package com.glindor.fotogeopointer.ui.listpoints

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.ui.PointRVAdapter
import com.glindor.fotogeopointer.ui.point.PointFragment
import com.glindor.fotogeopointer.utils.Logger
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
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

    private fun initViewModel(){
        listViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)

        listViewModel.getViewSate().observe(viewLifecycleOwner, {
            it?.let {
                it.points?.let { newList ->
                    adapter.points = newList }
            }
        })

    }
}