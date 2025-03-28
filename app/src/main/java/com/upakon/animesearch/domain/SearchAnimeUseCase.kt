package com.upakon.animesearch.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.upakon.animesearch.data.Anime
import com.upakon.animesearch.data.AnimePage
import com.upakon.animesearch.data.AnimeRepository
import com.upakon.animesearch.data.MAX_PER_PAGE
import com.upakon.animesearch.data.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    operator fun invoke(query: String): Flow<PagingData<Anime>> {
        return Pager(
            config = PagingConfig(MAX_PER_PAGE),
            initialKey = AnimeParams(page = 1, search = query),
            pagingSourceFactory = {
                AnimePagingSource(repository)
            }
        ).flow
    }

}