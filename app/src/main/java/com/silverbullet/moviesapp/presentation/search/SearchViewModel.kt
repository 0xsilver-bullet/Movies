package com.silverbullet.moviesapp.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.moviesapp.domain.model.SearchResult
import com.silverbullet.moviesapp.domain.repository.MoviesRepository
import com.silverbullet.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state

    private val _searchTextState = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    var shouldFocusSearchBar = true

    private var currentPage = 1
    private var searchJob: Job? = null

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.EditSearchQuery -> {
                _searchTextState.value = event.query
                if (event.query.isNotBlank() && event.query.isNotEmpty()) {
                    // First we must cancel any previous search jobs
                    searchJob?.cancel()
                    // value of current page must be reset to 1 as this is a new search query
                    // with a new results.
                    currentPage = 1
                    search()
                }
            }
            SearchScreenEvent.FetchMoreResults -> {
                if (!_state.value.isLoading) {
                    // Not loading then we can fetch more results in case there is more available pages
                    _state.value.searchResult?.let { searchResult ->
                        if (currentPage < searchResult.totalPages) {
                            search()
                        }
                    }
                }
            }
        }
    }

    private fun search() {
        searchJob = viewModelScope.launch {
            delay(500L)
            repository
                .search(_searchTextState.value, page = currentPage)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            if (currentPage == 1) {
                                // First Search
                                _state.value = _state.value.copy(
                                    isLoading = false,
                                    error = null,
                                    searchResult = resource.data
                                )
                            } else {
                                // User is just scrolling and we need to fetch more results
                                val currentMoviesInfoList = _state.value.searchResult!!.movies
                                _state.value = _state.value.copy(
                                    isLoading = false,
                                    error = null,
                                    searchResult = SearchResult(
                                        totalPages = resource.data!!.totalPages,
                                        movies = buildList {
                                            addAll(currentMoviesInfoList)
                                            addAll(resource.data.movies)
                                        },
                                        page = resource.data.page,
                                        actors = resource.data.actors,
                                        totalResults = resource.data.totalResults
                                    )
                                )
                            }
                            currentPage++
                        }
                        is Resource.Error -> {
                            _state.value =
                                _state.value.copy(isLoading = false, error = resource.message)
                            Timber.d("Error : ${resource.message}")
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = null)
                        }
                    }
                }
        }
    }

}