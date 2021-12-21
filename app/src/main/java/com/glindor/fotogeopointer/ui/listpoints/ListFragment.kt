package com.glindor.fotogeopointer.ui.listpoints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.databinding.FragmentFirstBinding
import com.glindor.fotogeopointer.ui.PointRVAdapter
import com.glindor.fotogeopointer.ui.base.BaseFragment
import com.glindor.fotogeopointer.ui.point.PointFragment
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : BaseFragment<List<Point>?, ListViewSate>() {

    override val viewModel: ListViewModel by viewModel()
    private lateinit var adapter: PointRVAdapter
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        root = binding.root
        initViewModel()
        initUserView()
        return root
    }

    private fun initUserView() {
        val recyclerViewManager = GridLayoutManager(requireContext(), 2)
        binding.rvPoints.layoutManager = recyclerViewManager
        adapter = PointRVAdapter(onClickListener = {
            PointFragment.start(this, it)
        })
        binding.rvPoints.adapter = adapter
        binding.fab.setOnClickListener {
            PointFragment.start(this)
        }
    }

    override fun renderData(newData: List<Point>?) {
        newData?.let {
            adapter.points = it
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}