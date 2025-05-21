package com.emanh.rootapp.data.db.service

import com.emanh.rootapp.data.db.entity.MusixmatchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MusixmatchService {
    @GET("matcher.lyrics.get")
    suspend fun getLyrics(
        @Query("q_track") trackName: String,
        @Query("q_artist") artistName: String,
        @Query("apikey") apiKey: String,
    ): MusixmatchResponse
}