package com.upakon.animesearch.di

import com.upakon.animesearch.data.AnimeRepository
import com.upakon.animesearch.data.AnimeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        animeRepositoryImpl: AnimeRepositoryImpl
    ): AnimeRepository

}