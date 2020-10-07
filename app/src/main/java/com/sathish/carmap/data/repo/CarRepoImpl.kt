package com.sathish.carmap.data.repo

import com.sathish.carmap.data.model.CarResponseApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import safeApiCall

class CarRepoImpl(
    private val repo: CarRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CarRepository {
    override suspend fun getCarsList(): Result<CarResponseApi> {
        return safeApiCall(dispatcher) { repo.getCarsList() }
    }

}