package com.emanh.rootapp.data.datasource

import android.util.Log
import com.emanh.rootapp.data.db.service.MusixmatchService
import javax.inject.Inject

private const val TAG = "MusixmatchDataSource"

class MusixmatchDataSourceImpl @Inject constructor(
    private val musixmatchService: MusixmatchService,
) : MusixmatchDataSource {

    companion object {
        private const val API_KEY = "be3d1d0919c2c7026f62f21e97058587"
    }

    override suspend fun getLyrics(trackName: String, artistName: String): String? {
        return try {
            val response = musixmatchService.getLyrics(trackName, artistName, API_KEY)

            when (response.message.header.status_code) {
                200 -> {
                    response.message.body.lyrics?.lyrics_body?.let { lyrics ->
                        if (lyrics.isNotBlank()) {
                            Log.d(TAG, "Successfully retrieved lyrics")
                            lyrics
                        } else {
                            Log.w(TAG, "Empty lyrics body received")
                            null
                        }
                    } ?: run {
                        Log.w(TAG, "No lyrics found in response body")
                        null
                    }
                }

                401 -> {
                    Log.e(TAG, "API Error: Unauthorized - Invalid API key")
                    null
                }

                404 -> {
                    Log.e(TAG, "API Error: Lyrics not found for track: $trackName, artist: $artistName")
                    null
                }

                429 -> {
                    Log.e(TAG, "API Error: Rate limit exceeded")
                    null
                }

                else -> {
                    Log.e(TAG, "API Error: Unexpected status code ${response.message.header.status_code}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception while fetching lyrics: ${e.message}", e)
            null
        }
    }
}
