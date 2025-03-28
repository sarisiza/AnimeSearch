package com.upakon.animesearch.domain

import com.upakon.animesearch.data.MAX_PER_PAGE

data class AnimeParams(
    val perPage: Int = MAX_PER_PAGE,
    val page: Int,
    val search: String? = null
)
