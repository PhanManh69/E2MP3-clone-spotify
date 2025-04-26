package com.emanh.rootapp.domain.model.crossref

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.emanh.rootapp.data.db.entity.GenresEntity
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity

data class SongsWithGenresModel(
    @Embedded val songsModel: SongsEntity,
    @Relation(parentColumn = "songId", entityColumn = "genreId", associateBy = Junction(SongGenreEntity::class)) val genresList: List<GenresEntity>
)