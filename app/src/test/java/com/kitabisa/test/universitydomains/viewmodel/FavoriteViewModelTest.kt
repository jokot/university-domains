package com.kitabisa.test.universitydomains.viewmodel

import app.cash.turbine.test
import com.kitabisa.test.universitydomains.core.data.repository.FavoriteRepository
import com.kitabisa.test.universitydomains.core.data.repository.FavoriteRepositoryImpl
import com.kitabisa.test.universitydomains.core.testing.dao.TestUniversityDao
import com.kitabisa.test.universitydomains.core.testing.data.UniversityTestData
import com.kitabisa.test.universitydomains.core.testing.datasource.TestLocalDataSource
import com.kitabisa.test.universitydomains.core.testing.repository.TestFavoriteRepository
import com.kitabisa.test.universitydomains.core.testing.util.MainDispatcherRule
import com.kitabisa.test.universitydomains.feature.favorite.FavoritesUiState
import com.kitabisa.test.universitydomains.feature.favorite.FavoritesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDao = TestUniversityDao()
    private val testLocalDataSource = TestLocalDataSource()

    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        val testDispatcher = UnconfinedTestDispatcher()

        favoriteRepository = FavoriteRepositoryImpl(
            localDataSource = testLocalDataSource,
            universityDao = testDao,
            dispatcher = testDispatcher
        )

        favoriteRepository = TestFavoriteRepository(favoriteRepository)

        viewModel = FavoritesViewModel(favoriteRepository)
    }

    @Test
    fun `when repository return success ui state should success`() = runTest {
        // Emit success network data
        testDao.sendUniversities(UniversityTestData.universities)
        testLocalDataSource.sendFavorites(UniversityTestData.favorites.toSet())

        // Collect flow states
        viewModel.uiState.test {
            // Assert loading state first
            assertIs<FavoritesUiState.Loading>(awaitItem())

            // Assert success state with expected data
            val successState = awaitItem()
            assertIs<FavoritesUiState.Success>(successState)
            assertEquals(UniversityTestData.favoriteSavableUniversities, successState.data)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when repository return empty ui state should empty`() = runTest {
        // Emit empty network data
        testDao.sendUniversities(emptyList())

        viewModel.uiState.test {
            // Assert loading state first
            assertIs<FavoritesUiState.Loading>(awaitItem())

            // Assert empty state
            val emptyState = awaitItem()
            assertIs<FavoritesUiState.Empty>(emptyState)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when repository return error ui state should error`() = runTest {
        // Simulate an error in the local data source
        testLocalDataSource.simulateError(UniversityTestData.errorMessage)

        viewModel.uiState.test {
            assertIs<FavoritesUiState.Loading>(awaitItem())

            val errorState = awaitItem()
            assertIs<FavoritesUiState.Error>(errorState)
            assertEquals(UniversityTestData.errorMessage, errorState.message)

            cancelAndIgnoreRemainingEvents()
        }

        // Clear the error for other tests
        testLocalDataSource.clearError()
    }

}
