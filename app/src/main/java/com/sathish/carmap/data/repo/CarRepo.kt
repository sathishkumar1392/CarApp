package com.sathish.carmap.data.repo

import com.sathish.carmap.data.endpointpath.EndPointPath
import com.sathish.carmap.data.model.CarResponseApi
import retrofit2.http.GET

interface CarRepo {

    @GET(EndPointPath.Cars.TAG_CARS)
    suspend fun getCarsList(): CarResponseApi
}