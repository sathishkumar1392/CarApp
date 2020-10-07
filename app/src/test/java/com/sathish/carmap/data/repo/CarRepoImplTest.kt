package com.sathish.carmap.data.repo

import com.sathish.carmap.data.model.CarResponseApi
import com.sathish.carmap.data.repo.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class CarRepoImplTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val api: CarRepo = mockk()


    private val repository = CarRepoImpl(api, testDispatcher)


    @After
    fun cleanup() {
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun getCarsList() {

        coEvery {
            api.getCarsList()
        } answers{
            CarResponseApi()
        }

        runBlockingTest {
            val result = repository.getCarsList()
            assertEquals(Result.Success(CarResponseApi()),result)
        }

    }
}