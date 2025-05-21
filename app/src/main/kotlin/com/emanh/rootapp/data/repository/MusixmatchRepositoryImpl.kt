package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.MusixmatchDataSource
import com.emanh.rootapp.domain.repository.MusixmatchRepository
import javax.inject.Inject

class MusixmatchRepositoryImpl @Inject constructor(
    private val musixmatchDataSource: MusixmatchDataSource,
) : MusixmatchRepository {
    override suspend fun getLyrics(trackName: String, artistName: String): String? {
        return musixmatchDataSource.getLyrics(trackName, artistName)
    }
}