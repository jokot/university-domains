package com.example.universitydomains.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.example.universitydomains.core.data.repository.RecentSearchesRepository
import com.example.universitydomains.core.data.repository.RecentSearchesRepositoryImpl
import com.example.universitydomains.core.data.repository.SearchRepository
import com.example.universitydomains.core.data.repository.SearchRepositoryImpl
import com.example.universitydomains.core.network.NetworkResult
import com.example.universitydomains.core.testing.dao.TestRecentSearchDao
import com.example.universitydomains.core.testing.dao.TestUniversityDao
import com.example.universitydomains.core.testing.data.UniversityTestData
import com.example.universitydomains.core.testing.datasource.TestLocalDataSource
import com.example.universitydomains.core.testing.datasource.TestNetworkDataSource
import com.example.universitydomains.core.testing.repository.TestSearchRepository
import com.example.universitydomains.core.testing.util.MainDispatcherRule
import com.example.universitydomains.feature.search.RecentSearchesUiState
import com.example.universitydomains.feature.search.SearchResultUiState
import com.example.universitydomains.feature.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testUniversityDao = TestUniversityDao()
    private val testRecentSearchDao = TestRecentSearchDao()
    private val testLocalDataSource = TestLocalDataSource()
    private val testNetworkDataSource = TestNetworkDataSource()

    private lateinit var searchRepository: SearchRepository
    private lateinit var recentSearchesRepository: RecentSearchesRepository
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        val testDispatcher = UnconfinedTestDispatcher()

        val realSearchRepository = SearchRepositoryImpl(
            networkDataSource = testNetworkDataSource,
            localDataSource = testLocalDataSource,
            universityDao = testUniversityDao,
            dispatcher = testDispatcher
        )

        searchRepository = TestSearchRepository(realSearchRepository)

        recentSearchesRepository = RecentSearchesRepositoryImpl(
            recentSearchDao = testRecentSearchDao,
            dispatcher = testDispatcher
        )

        viewModel = SearchViewModel(
            searchRepository = searchRepository,
            recentSearchesRepository = recentSearchesRepository
        )
    }

    @Test
    fun `when query is updated searchResultUiState should switch to Idle`() = runTest {
        viewModel.updateQuery(TextFieldValue(""))
        viewModel.searchResultUiState.test {
            assertIs<SearchResultUiState.Idle>(awaitItem())
        }
    }

    @Test
    fun `when repository returns success searchResultUiState should success`() = runTest {
        val query = UniversityTestData.query
        val expectedData = UniversityTestData.universitiesFilterByQuery

        // Emit success network data
        testNetworkDataSource.sendNetworkResult(
            NetworkResult.Success(UniversityTestData.networkUniversities)
        )
        testNetworkDataSource.searchUniversity(query)

        viewModel.performSearch(query)

        viewModel.searchResultUiState.test {
            assertIs<SearchResultUiState.Loading>(awaitItem())
            val successState = awaitItem()
            assertIs<SearchResultUiState.Success>(successState)
            assertEquals(expectedData, successState.data)
        }
    }

    @Test
    fun `when repository returns empty searchResultUiState should empty`() = runTest {
        val query = UniversityTestData.unknown

        // Emit success network data
        testNetworkDataSource.sendNetworkResult(
            NetworkResult.Success(UniversityTestData.networkUniversities)
        )
        testNetworkDataSource.searchUniversity(query)

        viewModel.performSearch(query)

        viewModel.searchResultUiState.test {
            assertIs<SearchResultUiState.Loading>(awaitItem())
            assertIs<SearchResultUiState.Empty>(awaitItem())
        }
    }

    @Test
    fun `when repository returns error searchResultUiState should error`() = runTest {
        val query = UniversityTestData.query
        val errorMessage = UniversityTestData.errorMessage

        // Emit error network data
        testNetworkDataSource.sendNetworkResult(
            NetworkResult.Error(errorMessage)
        )

        viewModel.performSearch(query)

        viewModel.searchResultUiState.test {
            assertIs<SearchResultUiState.Loading>(awaitItem()) // Assert Loading state
            val errorState = awaitItem()
            assertIs<SearchResultUiState.Error>(errorState) // Assert Error state
            assertEquals(errorMessage, errorState.message)

            cancelAndConsumeRemainingEvents() // Avoid unconsumed events error
        }
    }

    @Test
    fun `when recent searches are updated recentSearchesUiState should reflect changes`() =
        runTest {
            val recentSearche = UniversityTestData.recentSearch

            testRecentSearchDao.sendRecentSearch(recentSearche)

            viewModel.recentSearchesUiState.test {
                val recentState = awaitItem()
                assertIs<RecentSearchesUiState.Recent>(recentState)
                assertEquals(recentSearche, recentState.data)
            }
        }

    @Test
    fun `when recent searches are cleared recentSearchesUiState should be empty`() = runTest {
        testRecentSearchDao.clearRecentSearch()

        viewModel.recentSearchesUiState.test {
            assertIs<RecentSearchesUiState.Empty>(awaitItem())
        }
    }
}
