package com.upakon.animesearch.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.upakon.animesearch.BuildConfig
import com.upakon.animesearch.data.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        certificatePinner: CertificatePinner
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .certificatePinner(certificatePinner)
        .build()

    @Provides
    @Singleton
    fun providesApolloClient(
        okHttpClient: OkHttpClient
    ): ApolloClient = ApolloClient
        .Builder()
        .serverUrl(BASE_URL)
        .okHttpClient(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun providesCertificatePinner(): CertificatePinner =
        CertificatePinner
            .Builder()
            .add(BASE_URL,BuildConfig.apipublickey)
            .build()

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO

}