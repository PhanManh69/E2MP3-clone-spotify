package com.emanh.rootapp.data.db.fakedata

import android.content.Context
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.GenresEntity

fun fakeGenresData(context: Context): List<GenresEntity> {
    val genresData = listOf(GenresEntity(genreId = 0, name = context.getString(R.string.genre_chill)),
                            GenresEntity(genreId = 1, name = context.getString(R.string.genre_hip_hop)),
                            GenresEntity(genreId = 2, name = context.getString(R.string.genre_energizing)),
                            GenresEntity(genreId = 3, name = context.getString(R.string.genre_sol)),
                            GenresEntity(genreId = 4, name = context.getString(R.string.genre_romantic)),
                            GenresEntity(genreId = 5, name = context.getString(R.string.genre_party)),
                            GenresEntity(genreId = 6, name = context.getString(R.string.genre_concentrate)),
                            GenresEntity(genreId = 7, name = context.getString(R.string.genre_blues)),
                            GenresEntity(genreId = 8, name = context.getString(R.string.genre_indie)),
                            GenresEntity(genreId = 9, name = context.getString(R.string.genre_jazz)),
                            GenresEntity(genreId = 10, name = context.getString(R.string.genre_lating)),
                            GenresEntity(genreId = 11, name = context.getString(R.string.genre_edm)),
                            GenresEntity(genreId = 12, name = context.getString(R.string.genre_v_pop)),
                            GenresEntity(genreId = 13, name = context.getString(R.string.genre_pop)),
                            GenresEntity(genreId = 14, name = context.getString(R.string.genre_r_and_b)),
                            GenresEntity(genreId = 15, name = context.getString(R.string.genre_rook)))

    return genresData
}