package com.sathish.carmap.utils

import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException
import com.sathish.carmap.data.repo.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NetworkHelperKtTest {


    private val dispatcher = TestCoroutineDispatcher()


    @Test
    fun `it shows successfully when it should emit the result as success`() {

        runBlockingTest {
            val lambdaResult = true
            val result = safeApiCall(dispatcher) { lambdaResult }
            assertEquals(Result.Success(lambdaResult),result)
        }

    }


    @Test
    fun `it throws IOException when it should emit the result as NetworkError`() {

        runBlockingTest {
            val result = safeApiCall(dispatcher) { throw IOException() }
            assertEquals(result, Result.NetworkError)
        }
    }


    @Test
    fun `when lambda throws unknown exception then it should emit GenericError`() {
        runBlockingTest {
            val result = safeApiCall(dispatcher) {
                throw IllegalStateException()
            }
            assertEquals(Result.GenericError(), result)
        }
    }

}