package com.emanh.rootapp.domain.model.crossref

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.emanh.rootapp.data.db.entity.AlbumsEntity
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumSongEntity

data class CrossRefAlbumsModel(
    @Embedded val albums: AlbumsEntity,
    @Relation(parentColumn = "albumId", entityColumn = "songId", associateBy = Junction(AlbumSongEntity::class)) val songsList: List<SongsEntity>,
    @Relation(parentColumn = "albumId", entityColumn = "userId", associateBy = Junction(AlbumArtistEntity::class)) val artistsList: List<UsersEntity>
)