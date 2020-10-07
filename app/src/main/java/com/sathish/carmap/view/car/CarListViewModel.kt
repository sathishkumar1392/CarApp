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
    private val markerLiveData: MutableLiveData<List<CarResponseApiItem>>? = MutableLiveData()

    private var carList: MutableList<CarResponseApiItem> = ArrayList<CarResponseApiItem>()
    private val carLiveData: MutableLiveData<List<CarResponseApiItem>>? = MutableLiveData()



   /* init {
        getMarkerData()
    }*/

     fun getMarkerData() {
        viewModelScope.launch {
            when (val response = repository.getCarsList()) {
                is Result.Success -> showSuccess(response.value)
                is Result.NetworkError -> showNetworkError()
                is Result.GenericError -> showGenericError(response)
            }
        }

    }


    private suspend fun showSuccess(result: CarResponseApi) {
        withContext(Dispatchers.Main) {
            carList = result
            markerList = result
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



    internal fun sortByNumberPlate() {
        if (carList.isNotEmpty()) {
            val sortedByNumberPlate = carList.sortedBy {
                it.plateNumber

            }
            carLiveData?.postValue(sortedByNumberPlate)
        }
    }

    internal fun sortByBattery() {
        if (carList.isNotEmpty()) {
            val sortedByBattery = carList.sortedBy {
                it.batteryPercentage

            }
            carLiveData?.postValue(sortedByBattery)
        }
    }

    internal fun sortByDistance() {
        if (carList.isNotEmpty()) {
            val sortedByDistance = carList.sortedBy {
                it.batteryEstimatedDistance
            }
            carLiveData?.postValue(sortedByDistance)
        }
    }

}