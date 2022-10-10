package com.silverbullet.moviesapp.presentation.search

sealed class SearchScreenEvent{

    class EditSearchQuery(val query: String): SearchScreenEvent()
    object FetchMoreResults: SearchScreenEvent()
}
