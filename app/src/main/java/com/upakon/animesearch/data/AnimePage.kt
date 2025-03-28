package com.upakon.animesearch.data

import com.upakon.animesearch.AnimeQuery

data class AnimePage(
    val animeList: List<Anime>,
    val pageMetadata: PageMetadata
)

data class Anime(
    val title: String,
    val description: String,
    val siteUrl: String?,
    val bannerImage: String?,
    val score: Int
)

data class PageMetadata(
    val lastPage: Int,
    val perPage: Int,
    val currentPage: Int
)

fun AnimeQuery.Data.toDomain(): AnimePage {
    val page = this.Page?.pageInfo
    val metadata = page?.let {
        PageMetadata(
            it.lastPage ?: 1,
            it.perPage ?: 1,
            it.currentPage ?: 1
        )
    } ?: PageMetadata(1,1,1)
    val list = this.Page?.media?.map {
        Anime(
            it?.title?.english ?: it?.title?.native ?: "Unknown",
            it?.description ?: "Unknown",
            it?.siteUrl,
            it?.bannerImage,
            it?.averageScore ?: 0
        )
    } ?: emptyList()
    return  AnimePage(list,metadata)
}