package com.silverbullet.moviesapp.presentation.search

import com.silverbullet.moviesapp.domain.model.SearchResult

data class SearchScreenState(
    val isLoading: Boolean = false,
    val searchResult: SearchResult? = null,
    val error: String? = null
)
