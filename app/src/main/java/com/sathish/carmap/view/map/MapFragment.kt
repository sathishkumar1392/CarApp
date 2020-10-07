package com.sathish.carmap.view.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.sathish.carmap.R
import com.sathish.carmap.base.BaseMapMarkerFragment
import com.sathish.carmap.databinding.FragmentCarMapBinding
import com.sathish.carmap.view.car.CarListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : BaseMapMarkerFragment() {


    private lateinit var binding: FragmentCarMapBinding

    private val carListViewModel: CarListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarMapBinding.inflate(inflater, container, false)
        setObserver()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onClick()
    }

    private fun onClick() {
        binding.btnSwitchToList.setOnClickListener {
            it.findNavController().navigate(R.id.action_mapFragment_to_carListFragment)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMap(savedInstanceState, binding.map)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.setGroupVisible(R.id.group_car, false)
    }



    private fun setObserver() {

        carListViewModel.getMarkerLiveData()?.observe(viewLifecycleOwner, this::addMarker)


        carListViewModel.errorMessage.observe(viewLifecycleOwner, {
            when (it) {
                getString(R.string.str_network_error) -> showMessage(it)
                else -> showMessage(it)
            }

        })
    }

}