package com.upakon.animesearch.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.upakon.animesearch.data.Anime
import com.upakon.animesearch.data.AnimeRepository
import com.upakon.animesearch.data.toDomain
import javax.inject.Inject

class AnimePagingSource(
    private val repository: AnimeRepository
) : PagingSource<AnimeParams, Anime>(){

    override fun getRefreshKey(state: PagingState<AnimeParams, Anime>): AnimeParams {
        return AnimeParams(page = state.anchorPosition?:1)
    }

    override suspend fun load(params: LoadParams<AnimeParams>): LoadResult<AnimeParams, Anime> {
        return try {
            val currentPage = params.key?.page ?: 1
            val perPage = params.key?.perPage ?: 15
            val search = params.key?.search
            val response = repository.getAnimeList(
                currentPage,
                perPage,
                search
            )
            val animePage = response.dataOrThrow().toDomain()
            val current = animePage.pageMetadata.currentPage
            val last = animePage.pageMetadata.lastPage
            val prevKey = if(current <= 1) null else AnimeParams(page = currentPage - 1, search = search)
            val nextKey = if(current >= last) null else AnimeParams(page = currentPage + 1, search = search)
            LoadResult.Page(
                data = response.dataOrThrow().toDomain().animeList,
                nextKey = nextKey,
                prevKey = prevKey
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }


}