package com.emanh.rootapp.data.db.fakedata.crossref

import com.emanh.rootapp.data.db.entity.crossref.AlbumSongEntity
import com.emanh.rootapp.data.db.fakedata.fakeAlbumsData

fun fakeCrossRefAlbumSongData(): List<AlbumSongEntity> {
    val albumData = fakeAlbumsData()
    val crossRefList = mutableListOf<AlbumSongEntity>()

    albumData.forEachIndexed { index, item ->
        val albumId = index + 1

        item.songsIdList.forEach { songId ->
            crossRefList.add(AlbumSongEntity(albumId, songId))
        }
    }

    return crossRefList
}