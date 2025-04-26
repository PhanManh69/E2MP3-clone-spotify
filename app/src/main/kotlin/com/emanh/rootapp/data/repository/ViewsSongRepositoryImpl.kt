package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.ViewsSongDataSource
import com.emanh.rootapp.data.db.entity.ViewsSongEntity
import com.emanh.rootapp.domain.model.ViewsSongModel
import com.emanh.rootapp.domain.repository.ViewsSongRepository
import javax.inject.Inject

class ViewsSongRepositoryImpl @Inject constructor(
    private val viewsSongDataSource: ViewsSongDataSource
) : ViewsSongRepository {
    override suspend fun findViewRecord(userId: Int, songId: Int): ViewsSongModel? {
        val entity = viewsSongDataSource.findViewRecord(userId, songId)
        return entity?.let { mapToModel(it) }
    }

    override suspend fun updateViewsSong(viewsSong: ViewsSongModel) {
        viewsSongDataSource.updateViewsSong(mapToEntity(viewsSong))
    }

    override suspend fun insertViewsSong(viewsSong: ViewsSongModel) {
        viewsSongDataSource.insertViewsSong(mapToEntity(viewsSong))
    }

    private fun mapToEntity(model: ViewsSongModel): ViewsSongEntity {
        return ViewsSongEntity(viewsSongId = model.viewsSongId,
                               userId = model.userId,
                               songId = model.songId,
                               numberListener = model.numberListener,
                               dateTime = model.dateTime)
    }

    private fun mapToModel(entity: ViewsSongEntity): ViewsSongModel {
        return ViewsSongModel(viewsSongId = entity.viewsSongId,
                              userId = entity.userId,
                              songId = entity.songId,
                              numberListener = entity.numberListener,
                              dateTime = entity.dateTime)
    }
}