package com.silverbullet.moviesapp.presentation.search

import androidx.lifecycle.ViewModel
import com.silverbullet.moviesapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {


}