package com.emanh.rootapp.domain.model.crossref

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity

data class PlaylistWithSongModel(
    @Embedded val playlists: PlaylistsEntity,
    @Relation(parentColumn = "playlistId", entityColumn = "songId", associateBy = Junction(PlaylistSongEntity::class))
    val songsList: List<SongsEntity>
)