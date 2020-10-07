package com.sathish.carmap.data.repo

import com.sathish.carmap.data.model.CarResponseApi

interface CarRepository {

    suspend fun getCarsList():Result<CarResponseApi>

}