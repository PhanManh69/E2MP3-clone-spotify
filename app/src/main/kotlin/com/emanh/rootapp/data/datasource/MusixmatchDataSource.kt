package com.emanh.rootapp.data.datasource

interface MusixmatchDataSource {
    suspend fun getLyrics(
        trackName: String,
        artistName: String,
    ): String?
}