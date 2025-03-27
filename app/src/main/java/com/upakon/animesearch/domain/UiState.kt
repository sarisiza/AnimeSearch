package com.upakon.animesearch.domain

sealed class UiState<out T> {

    data object LOADING: UiState<Nothing>()
    data class SUCCESS<T>(val response: T): UiState<T>()
    data class ERROR(val e: Exception): UiState<Nothing>()

}