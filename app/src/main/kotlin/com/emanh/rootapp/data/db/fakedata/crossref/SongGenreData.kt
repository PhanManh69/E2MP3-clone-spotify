package com.emanh.rootapp.data.db.fakedata.crossref

import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.data.db.fakedata.fakeSongsData

fun fakeCrossRefSongGenreData(): List<SongGenreEntity> {
    val songsData = fakeSongsData()

    val crossRefList = mutableListOf<SongGenreEntity>()

    songsData.forEachIndexed { index, item ->
        val songId = index + 1

        item.genresIdList?.forEach { genreId ->
            crossRefList.add(SongGenreEntity(songId, genreId))
        }
    }

    return crossRefList
}