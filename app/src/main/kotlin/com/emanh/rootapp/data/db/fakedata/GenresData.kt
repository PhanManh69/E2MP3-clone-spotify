package com.emanh.rootapp.data.db.fakedata

import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.GenresEntity

fun fakeGenresData(): List<GenresEntity> {
    val genresData = listOf(GenresEntity(genreId = 0, nameId = R.string.genre_chill),
                            GenresEntity(genreId = 1, nameId = R.string.genre_hip_hop),
                            GenresEntity(genreId = 2, nameId = R.string.genre_energizing),
                            GenresEntity(genreId = 3, nameId = R.string.genre_sol),
                            GenresEntity(genreId = 4, nameId = R.string.genre_romantic),
                            GenresEntity(genreId = 5, nameId = R.string.genre_party),
                            GenresEntity(genreId = 6, nameId = R.string.genre_concentrate),
                            GenresEntity(genreId = 7, nameId = R.string.genre_blues),
                            GenresEntity(genreId = 8, nameId = R.string.genre_indie),
                            GenresEntity(genreId = 9, nameId = R.string.genre_jazz),
                            GenresEntity(genreId = 10, nameId = R.string.genre_lating),
                            GenresEntity(genreId = 11, nameId = R.string.genre_edm),
                            GenresEntity(genreId = 12, nameId = R.string.genre_v_pop),
                            GenresEntity(genreId = 13, nameId = R.string.genre_pop),
                            GenresEntity(genreId = 14, nameId = R.string.genre_r_and_b),
                            GenresEntity(genreId = 15, nameId = R.string.genre_rook))

    return genresData
}