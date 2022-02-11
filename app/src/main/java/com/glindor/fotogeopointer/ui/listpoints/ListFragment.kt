package com.glindor.fotogeopointer.ui.listpoints

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.databinding.FragmentFirstBinding
import com.glindor.fotogeopointer.ui.PointRVAdapter
import com.glindor.fotogeopointer.ui.base.BaseFragment
import com.glindor.fotogeopointer.ui.point.PointFragment
import com.glindor.fotogeopointer.ui.splash.SplashActivity
import com.glindor.fotogeopointer.utils.Logger
import org.jetbrains.anko.support.v4.alert
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : BaseFragment<FragmentFirstBinding, List<Point>?, ListViewSate>(FragmentFirstBinding::inflate) {

    override val viewModel: ListViewModel by viewModel()
    private lateinit var adapter: PointRVAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val viewRoot = super.onCreateView(inflater, container, savedInstanceState)
        Logger.d(this,"ListFragment onCreateView container = $container")
        setHasOptionsMenu(true)
        initViewModel()
        initUserView()
        return viewRoot
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
        setTitle(getString(R.string.list_title))
    }

    override fun renderData(newData: List<Point>?) {
        newData?.let {
            Logger.d(this,"ListFragment renderData")
            adapter.points = it
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> true
        R.id.action_logout -> {
            showLogoutDialog()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    private fun showLogoutDialog() {
        alert{
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.logout_dialog_button_positive) { logOut() }
            negativeButton(R.string.logout_dialog_button_negative) { dialog ->  dialog.cancel()}
        }.show()
    }

    private fun logOut() {
        Logger.d(this,"logOut()")
        AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener{
                    Logger.d(this,"signOut() complete")
                    SplashActivity.start(requireContext())
                }
    }


}