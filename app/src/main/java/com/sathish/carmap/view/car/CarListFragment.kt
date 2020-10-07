package com.sathish.carmap.view.car

import android.os.Bundle
import android.view.*
import com.sathish.carmap.R
import com.sathish.carmap.base.BaseFragment
import com.sathish.carmap.databinding.FragmentCarListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarListFragment : BaseFragment() {

    private lateinit var binding: FragmentCarListBinding
    private val carListViewModel: CarListViewModel by viewModel()
    private val adapter = CarAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarListBinding.inflate(inflater, container, false)
        binding.viewModel = carListViewModel
        binding.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObserver()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.setGroupVisible(R.id.group_car, true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_plateNumber -> {
                carListViewModel.sortByNumberPlate()
                return true
            }
            R.id.action_battery -> {
                carListViewModel.sortByBattery()
                return true
            }

            R.id.action_distance -> {
                carListViewModel.sortByDistance()
                return true
            }

        }
        return super.onOptionsItemSelected(item)

    }


    private fun setObserver() {

        carListViewModel.getCarLiveData()?.observe(viewLifecycleOwner, {
            adapter.update(it)

        })
    }


}