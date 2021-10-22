package com.glindor.fotogeopointer.ui.point

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.glindor.fotogeopointer.ui.listpoints.ListViewModel
import com.glindor.fotogeopointer.utils.Logger
import kotlinx.android.synthetic.main.fragment_second.*
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PointFragment : Fragment()  {

    private lateinit var pointViewModel: PointViewModel
    private var point:Point? = null

    companion object{
        private val REQUEST_POINT = PointFragment::class.java.name + "REQUEST_POINT"
        private val BUNDLE_POINT = PointFragment::class.java.name + "BUNDLE_POINT"
        @JvmStatic

        fun start(view: Fragment, point: Point? = null) {
            point?.let {
                view.parentFragmentManager.setFragmentResult(
                    REQUEST_POINT,
                    bundleOf(BUNDLE_POINT to it)
                )
            }
            view.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserView()
        initViewModel()
        parentFragmentManager.setFragmentResultListener(REQUEST_POINT,this) {
                _, bundle ->
            Logger.d("a2")
            val result = bundle.getParcelable<Point>(BUNDLE_POINT)
            result?.let {
                point = it
                Logger.d("a3")
                initPointView(point)
            }

        }

        view.findViewById<Button>(R.id.btn_getGeo).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }


    }

    private fun initViewModel() {
        pointViewModel =
            ViewModelProvider(this).get(PointViewModel::class.java)
    }

    private val pointTextListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        override fun afterTextChanged(s: Editable?)  = savePoint()
    }

    private fun initUserView() {
        point_name.addTextChangedListener(pointTextListener)
        point_disc.addTextChangedListener(pointTextListener)
        point_lati.addTextChangedListener(pointTextListener)
        point_longi.addTextChangedListener(pointTextListener)
    }

    private fun initPointView(inPoint: Point? = null) {
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

    fun savePoint() {
        point = point?.copy(
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

        point?.let {
            pointViewModel.savePoint(it)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            Logger.d("onOptionsItemSelected android.R.id.home")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
/*
    override fun onBackPressed(): Boolean {
        Logger.d("fragment onBackPressed")
        savePoint()
        return  true
    }

 */
}