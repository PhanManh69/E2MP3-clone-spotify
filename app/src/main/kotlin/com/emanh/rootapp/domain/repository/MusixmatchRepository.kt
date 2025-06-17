package com.emanh.rootapp.domain.repository

interface MusixmatchRepository {
    suspend fun getLyrics(
        trackName: String,
        artistName: String,
    ): String?
}