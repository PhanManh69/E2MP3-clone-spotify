package com.emanh.rootapp.data.db.fakedata

import com.emanh.rootapp.R
import com.emanh.rootapp.data.db.entity.GenresEntity

fun fakeGenresData(): List<GenresEntity> {
    val genresData = listOf(GenresEntity(id = 0, nameId = R.string.genre_chill),
                            GenresEntity(id = 1, nameId = R.string.genre_hip_hop),
                            GenresEntity(id = 2, nameId = R.string.genre_energizing),
                            GenresEntity(id = 3, nameId = R.string.genre_sol),
                            GenresEntity(id = 4, nameId = R.string.genre_romantic),
                            GenresEntity(id = 5, nameId = R.string.genre_party),
                            GenresEntity(id = 6, nameId = R.string.genre_concentrate),
                            GenresEntity(id = 7, nameId = R.string.genre_blues),
                            GenresEntity(id = 8, nameId = R.string.genre_indie),
                            GenresEntity(id = 9, nameId = R.string.genre_jazz),
                            GenresEntity(id = 10, nameId = R.string.genre_lating),
                            GenresEntity(id = 11, nameId = R.string.genre_edm),
                            GenresEntity(id = 12, nameId = R.string.genre_v_pop),
                            GenresEntity(id = 13, nameId = R.string.genre_pop),
                            GenresEntity(id = 14, nameId = R.string.genre_r_and_b),
                            GenresEntity(id = 15, nameId = R.string.genre_rook))

    return genresData
}