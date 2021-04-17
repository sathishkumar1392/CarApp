package com.sathish.carmap.view.car


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sathish.carmap.R
import com.sathish.carmap.base.BaseViewModel
import com.sathish.carmap.data.model.CarResponseApi
import com.sathish.carmap.data.model.CarResponseApiItem
import com.sathish.carmap.data.repo.CarRepository
import com.sathish.carmap.data.repo.Result
import com.sathish.carmap.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CarListViewModel(
    private val repository: CarRepository,
    private val res: Resource
) : BaseViewModel() {


    private var markerList: MutableList<CarResponseApiItem> = ArrayList<CarResponseApiItem>()
    private val markerLiveData: MutableLiveData<List<CarResponseApiItem>> = MutableLiveData()

    private var carList: MutableList<CarResponseApiItem> = ArrayList<CarResponseApiItem>()
    private var sortingList: MutableList<CarResponseApiItem> = ArrayList<CarResponseApiItem>()
    private val carLiveData: MutableLiveData<List<CarResponseApiItem>> = MutableLiveData()


    init {
        getMarkerData()
    }

    fun getMarkerData() {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val response = repository.getCarsList()) {
                is Result.Success -> showSuccess(response.value)
                is Result.NetworkError -> showNetworkError()
                is Result.GenericError -> showGenericError(response)
            }
        }
        isLoading.postValue(false)
    }


    private suspend fun showSuccess(result: CarResponseApi) {
        withContext(Dispatchers.Main) {
            carList = result
            markerList = result
            sortingList = result
            markerLiveData?.postValue(markerList)
            carLiveData?.postValue(carList)
        }
    }

    private fun showGenericError(response: Result.GenericError) {
        errorMessage.postValue(response.error.toString())
    }


    private fun showNetworkError() {
        errorMessage.postValue(res.getString(R.string.str_network_error))

    }


    fun getMarkerLiveData(): MutableLiveData<List<CarResponseApiItem>>? {
        return markerLiveData
    }

    fun getCarLiveData(): MutableLiveData<List<CarResponseApiItem>>? {
        return carLiveData
    }


   internal fun listFilter(name: String) {
        when (name) {
            res.getString(R.string.str_car_plateNumber) -> {
                val sortedByNumberPlate = sortingList.sortedBy { sort ->
                    sort.plateNumber

                }
                carLiveData?.postValue(sortedByNumberPlate)
            }
            res.getString(R.string.str_car_remaining_battery) -> {
                val sortedByBattery = sortingList.sortedBy { sort ->
                    sort.batteryPercentage

                }
                carLiveData?.postValue(sortedByBattery)
            }

            res.getString(R.string.str_car_sort_by_distance_from_user) -> {
                val sortedByDistance = sortingList.sortedBy { sort ->
                    sort.batteryEstimatedDistance
                }
                carLiveData?.postValue(sortedByDistance)
            }

            res.getString(R.string.str_reset_list) -> {
                carLiveData?.postValue(carList)
            }

        }


    }

}