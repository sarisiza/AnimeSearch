package com.upakon.animesearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.upakon.animesearch.AnimeQuery
import com.upakon.animesearch.data.Anime
import com.upakon.animesearch.domain.GetAllAnimesUseCase
import com.upakon.animesearch.domain.SearchAnimeUseCase
import com.upakon.animesearch.domain.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val getAllAnime: GetAllAnimesUseCase,
    private val searchAnime: SearchAnimeUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _animeList: MutableStateFlow<PagingData<Anime>> = MutableStateFlow(PagingData.empty())
    val animeList = _animeList.asStateFlow()

    fun getAnimeList(){
        viewModelScope.launch(dispatcher) {
            getAllAnime().collect{
                _animeList.value = it
            }
        }
    }

    fun searchForAnime(query: String){
        viewModelScope.launch(dispatcher) {
            searchAnime(query).collect{
                _animeList.value = it
            }
        }
    }

}