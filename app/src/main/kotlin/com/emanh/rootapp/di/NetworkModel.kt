package com.emanh.rootapp.di

import com.emanh.rootapp.data.db.service.MusixmatchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideMusixmatchService(): MusixmatchService {
        return Retrofit.Builder()
            .baseUrl("https://api.musixmatch.com/ws/1.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusixmatchService::class.java)
    }
}