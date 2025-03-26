package com.upakon.animesearch.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.upakon.animesearch.AnimeQuery
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : AnimeRepository {
    override suspend fun getAnimeList(
        page: Int,
        perPage: Int,
        search: String?
    ): ApolloResponse<AnimeQuery.Data> {
        return search?.let {
            apolloClient
                .query(AnimeQuery(Optional.present(page),Optional.present(perPage),Optional.present(it)))
                .execute()
        } ?: apolloClient
            .query(AnimeQuery(Optional.present(page),Optional.present(perPage),Optional.absent()))
            .execute()
    }
}