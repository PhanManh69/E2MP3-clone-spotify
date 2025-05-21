package com.emanh.rootapp.data.db.fakedata.crossref

import com.emanh.rootapp.data.db.entity.crossref.SongArtistEntity
import com.emanh.rootapp.data.db.fakedata.fakeSongsData

fun fakeCrossRefSongArtistData(): List<SongArtistEntity> {
    val songsData = fakeSongsData()
    val crossRefList = mutableListOf<SongArtistEntity>()

    songsData.forEachIndexed { index, item ->
        val songId = index + 1

        item.artistsIdList?.forEach { artistsId ->
            crossRefList.add(SongArtistEntity(songId, artistsId))
        }
    }

    return crossRefList
}