package com.glindor.fotogeopointer.ui.point

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.ui.IOnBackPressed
import com.glindor.fotogeopointer.ui.base.BaseFragment
import com.glindor.fotogeopointer.utils.Logger
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_second.*
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PointFragment : BaseFragment<Point?, PointViewState>(), IOnBackPressed {

    override val viewModel: PointViewModel by lazy {
        ViewModelProvider(this).get(PointViewModel::class.java)
    }
    private var workPoint:Point? = null

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
        initViewModel()
        root = inflater.inflate(R.layout.fragment_second, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener(REQUEST_POINT,this) {
                _, bundle ->
            val result = bundle.getString(BUNDLE_POINT)
            result?.let {
                viewModel.loadPoint(it)
                initPointView(workPoint)
            }
        }
        initUserView(view)

    }
    private fun initUserView(view: View) {
        view.findViewById<Button>(R.id.btn_getGeo).setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun renderData(newData: Point?) {
        workPoint = newData
        initPointView(newData)
    }

    private fun initPointView(inPoint: Point? = null) {
        Logger.d(this,"initPointView $inPoint")
        inPoint?.let {
            point_name.setText(it.name)
            point_disc.setText(it.disc)
            point_lati.text = it.lati.toString()
            point_longi.text = it.longi.toString()
        } ?: let {
            point_name.setText(R.string.point_name)
            point_disc.setText(R.string.point_disc)
            point_lati.setText(R.string.lati_null)
            point_longi.setText(R.string.longi_null)
        }
    }

    private fun savePoint() {
        Logger.d(this,"savePoint1 $workPoint")
        workPoint = workPoint?.copy(
            name = point_name.text.toString(),
            disc = point_disc.text.toString(),
            lati = point_lati.text.toString().toFloat(),
            longi = point_longi.text.toString().toFloat()
        ) ?: Point(
            UUID.randomUUID().toString(),
            point_name.text.toString(),
            point_disc.text.toString(),
            point_lati.text.toString().toFloat(),
            point_longi.text.toString().toFloat()
        )
        Logger.d(this,"savePoint2 $workPoint")

        viewModel.savePoint(workPoint!!)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            Logger.d(this,"onOptionsItemSelected android.R.id.home")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(): Boolean {
        Logger.d(this,"fragment onBackPressed")
        savePoint()
        return true
    }


}