package com.kitabisa.test.universitydomains.viewmodel

import app.cash.turbine.test
import com.kitabisa.test.universitydomains.core.data.repository.HomeRepository
import com.kitabisa.test.universitydomains.core.data.repository.HomeRepositoryImpl
import com.kitabisa.test.universitydomains.core.network.NetworkResult
import com.kitabisa.test.universitydomains.core.testing.repository.TestHomeRepository
import com.kitabisa.test.universitydomains.core.testing.datasource.TestLocalDataSource
import com.kitabisa.test.universitydomains.core.testing.datasource.TestNetworkDataSource
import com.kitabisa.test.universitydomains.core.testing.dao.TestUniversityDao
import com.kitabisa.test.universitydomains.core.testing.data.UniversityTestData
import com.kitabisa.test.universitydomains.core.testing.util.MainDispatcherRule
import com.kitabisa.test.universitydomains.feature.home.HomeUiState
import com.kitabisa.test.universitydomains.feature.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDao = TestUniversityDao()
    private val testLocalDataSource = TestLocalDataSource()
    private val testNetworkDataSource = TestNetworkDataSource()

    private lateinit var homeRepository: HomeRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        val testDispatcher = UnconfinedTestDispatcher()

        homeRepository = HomeRepositoryImpl(
            networkDataSource = testNetworkDataSource,
            localDataSource = testLocalDataSource,
            universityDao = testDao,
            dispatcher = testDispatcher
        )

        val realRepository = HomeRepositoryImpl(
            networkDataSource = testNetworkDataSource,
            localDataSource = testLocalDataSource,
            universityDao = testDao,
            dispatcher = testDispatcher
        )

        homeRepository = TestHomeRepository(realRepository)

        viewModel = HomeViewModel(homeRepository)
    }

    @Test
    fun `when repository return success ui state should success`() = runTest {
        // Emit success network data
        testNetworkDataSource.sendNetworkResult(
            NetworkResult.Success(UniversityTestData.networkUniversities)
        )

        // Collect flow states
        viewModel.uiState.test {
            // Assert loading state first
            assertIs<HomeUiState.Loading>(awaitItem())

            // Assert success state with expected data
            val successState = awaitItem()
            assertIs<HomeUiState.Success>(successState)
            assertEquals(UniversityTestData.savableUniversities, successState.data)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when repository return empty ui state should empty`() = runTest {
        // Emit empty network data
        testNetworkDataSource.sendNetworkResult(
            NetworkResult.Success(emptyList())
        )

        viewModel.uiState.test {
            // Assert loading state first
            assertIs<HomeUiState.Loading>(awaitItem())

            // Assert empty state
            val emptyState = awaitItem()
            assertIs<HomeUiState.Empty>(emptyState)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when repository return error ui state should error`() = runTest {
        // Emit error network data
        testNetworkDataSource.sendNetworkResult(
            NetworkResult.Error(UniversityTestData.errorMessage)
        )

        viewModel.uiState.test {
            // Assert loading state first
            assertIs<HomeUiState.Loading>(awaitItem())

            // Assert error state with expected error message
            val errorState = awaitItem()
            assertIs<HomeUiState.Error>(errorState)
            assertEquals(UniversityTestData.errorMessage, errorState.message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
