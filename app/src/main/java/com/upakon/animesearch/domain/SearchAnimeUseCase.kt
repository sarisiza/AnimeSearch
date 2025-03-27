package com.upakon.animesearch.domain

import com.upakon.animesearch.AnimeQuery
import com.upakon.animesearch.data.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    private val perPage = 15
    private var page = 1

    operator fun invoke(query: String): Flow<UiState<AnimeQuery.Page>> = flow {
        emit(UiState.LOADING)
        val response = repository.getAnimeList(
            page,
            perPage,
            query
        )
        response.data?.Page?.let {
            emit(UiState.SUCCESS(it))
            if(page == it.pageInfo?.lastPage)
                page = 1
            else
                page ++
        } ?: throw Exception("Data is empty")
    }.catch { e ->
        if(e is Exception){
            emit(UiState.ERROR(e))
        }
    }

}