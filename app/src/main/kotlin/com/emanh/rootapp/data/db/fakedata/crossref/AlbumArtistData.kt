package com.emanh.rootapp.data.db.fakedata.crossref

import com.emanh.rootapp.data.db.entity.crossref.AlbumArtistEntity
import com.emanh.rootapp.data.db.fakedata.fakeAlbumsData

fun fakeCrossRefAlbumArtistData(): List<AlbumArtistEntity> {
    val albumData = fakeAlbumsData()
    val crossRefList = mutableListOf<AlbumArtistEntity>()

    albumData.forEachIndexed { index, item ->
        val albumId = index + 1

        item.artistsIdList?.forEach { userId ->
            crossRefList.add(AlbumArtistEntity(albumId, userId))
        }
    }

    return crossRefList
}