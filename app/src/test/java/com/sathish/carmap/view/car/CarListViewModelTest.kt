package com.sathish.carmap.view.car

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sathish.carmap.data.model.CarResponseApi
import com.sathish.carmap.data.model.CarResponseApiItem
import com.sathish.carmap.data.repo.CarRepository
import com.sathish.carmap.data.repo.Result
import com.sathish.carmap.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CarListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private val testDispatcher = TestCoroutineDispatcher()

    private val repo: CarRepository = mockk(relaxed = true)
    private val res: Resource = mockk(relaxed = true)

    private val errorMessageObserver: Observer<String> = mockk(relaxed = true)

    private val item = listOf(
        CarResponseApiItem()

    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `when init ready it should called repository to fetch Car List`() {
        val result = Result.Success(CarResponseApi())

        coEvery { repo.getCarsList() } returns result
        instantiate().getMarkerData()

        coVerify { repo.getCarsList() }
    }



    @Test
    fun `when repository fetching Data is Completed loading should be set false`() {
        val result = Result.Success(CarResponseApi())
        coEvery {
            repo.getCarsList()
        }.coAnswers {
            delay(1000)
            result
        }
        instantiate().getMarkerData()
        testDispatcher.advanceTimeBy(1000)

        coVerifyOrder {

            repo.getCarsList()
            errorMessageObserver.onChanged("Success")

        }
    }


    private fun instantiate(): CarListViewModel {
        val viewModel = CarListViewModel(repo, res)
        viewModel.errorMessage.observeForever(errorMessageObserver)
        return viewModel
    }

}