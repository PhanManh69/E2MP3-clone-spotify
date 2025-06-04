package com.emanh.rootapp.data.db.fakedata.crossref

import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.data.db.fakedata.fakePlaylistsData

fun fakeCrossRefPlaylistSongData(): List<PlaylistSongEntity> {
    val playlistData = fakePlaylistsData()
    val crossRefList = mutableListOf<PlaylistSongEntity>()

    playlistData.forEachIndexed { index, item ->
        val playlistId = index + 1L

        item.songsIdList.forEach { songId ->
            crossRefList.add(PlaylistSongEntity(playlistId, songId))
        }
    }

    return crossRefList
}