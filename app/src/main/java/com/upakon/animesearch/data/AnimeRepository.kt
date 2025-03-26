package com.upakon.animesearch.data

import com.apollographql.apollo.api.ApolloResponse
import com.upakon.animesearch.AnimeQuery

interface AnimeRepository {

    suspend fun getAnimeList(
        page: Int,
        perPage: Int,
        search: String?
    ): ApolloResponse<AnimeQuery.Data>

}