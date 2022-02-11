package com.glindor.fotogeopointer.ui.point

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.databinding.FragmentSecondBinding
import com.glindor.fotogeopointer.ui.IOnBackPressed
import com.glindor.fotogeopointer.ui.base.BaseFragment
import com.glindor.fotogeopointer.utils.Logger
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PointFragment : BaseFragment<FragmentSecondBinding, PointViewState.Data, PointViewState>(FragmentSecondBinding::inflate), IOnBackPressed {

    override val viewModel: PointViewModel by viewModel()
    private var edited = false

    companion object{
        private val REQUEST_POINT = PointFragment::class.java.name + "REQUEST_POINT"
        private val BUNDLE_POINT = PointFragment::class.java.name + "BUNDLE_POINT"
        @JvmStatic

        fun start(view: Fragment, point: Point? = null) {
            point?.let {
                view.parentFragmentManager.setFragmentResult(
                        REQUEST_POINT,
                        bundleOf(BUNDLE_POINT to it.id)
                )
            }
            view.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val viewRoot = super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)
        initViewModel()
        initUserView()
        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener(REQUEST_POINT, this) { _, bundle ->
            bundle.getString(BUNDLE_POINT)?.let {
                viewModel.loadPoint(it)
            }
        }
        initPointView(null)
    }
    private fun initUserView() {
        binding.btnGetGeo.setOnClickListener {
//            viewModel.deletePoint()
        }
    }

    override fun renderData(newData: PointViewState.Data) {
        if(newData.isDeleted) {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        } else {
            initPointView(newData.data)
        }
    }

    private fun initPointView(inPoint: Point? = null) {
        Logger.d(this, "initPointView $inPoint")
        var title = getString(R.string.title_point_new)
        with(binding) {
            inPoint?.let {
                title = getString(R.string.title_point)+it.name
                pointName.setText(it.name)
                pointDisc.setText(it.disc)
                pointLati.text = it.lati.toString()
                pointLongi.text = it.longi.toString()
            } ?: let {
                pointName.setText(R.string.point_name)
                pointDisc.setText(R.string.point_disc)
                pointLati.setText(R.string.lati_null)
                pointLongi.setText(R.string.longi_null)
                pointName.doAfterTextChanged { edited = true }
                pointDisc.doAfterTextChanged { edited = true }
            }
        }
        setTitle(title)
    }

    private fun finishAndSavePoint() {
        Logger.d(this, "finishAndSavePoint1 ")
        if (!edited)
            return
        with(binding) {
            val workPoint = Point(
                    UUID.randomUUID().toString(),
                    pointName.text.toString(),
                    pointDisc.text.toString(),
                    pointLati.text.toString().toFloat(),
                    pointLongi.text.toString().toFloat()
            )
            Logger.d(this, "finishAndSavePoint2 $workPoint")
            viewModel.savePoint(workPoint)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home ->
            Logger.d(this, "onOptionsItemSelected android.R.id.home").let { true }
        R.id.action_delete ->
            viewModel.deletePoint().let { true }
        else ->
            super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(): Boolean {
        Logger.d(this, "PointFragment onBackPressed")
        finishAndSavePoint()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_point, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}