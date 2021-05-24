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
    ): View {
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
                carListViewModel.listFilter(getString(R.string.str_car_plateNumber))
                println("Item Id : $item.itemId")
                return true
            }
            R.id.action_battery -> {
                carListViewModel.listFilter(getString(R.string.str_car_remaining_battery))
                return true
            }

            R.id.action_distance -> {
                carListViewModel.listFilter(getString(R.string.str_car_sort_by_distance_from_user))
                return true
            }

            R.id.action_resetList -> {
                carListViewModel.listFilter(getString(R.string.str_reset_list))
                return true
            }

        }
        return super.onOptionsItemSelected(item)

    }


    private fun setObserver() {

        carListViewModel.getCarLiveData()?.observe(viewLifecycleOwner, {
            it?.let {
                adapter.differ.submitList(it)
            }
        })


        carListViewModel.getIsLoading()
            .observe(viewLifecycleOwner) { aBoolean -> binding.visibility = aBoolean }

    }


}