package com.emanh.rootapp.data.db.fakedata

import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.GenresEntity

fun fakeGenresData(): List<GenresEntity> {
    val genresData = listOf(GenresEntity(nameId = R.string.genre_chill),
                            GenresEntity(nameId = R.string.genre_hip_hop),
                            GenresEntity(nameId = R.string.genre_energizing),
                            GenresEntity(nameId = R.string.genre_sol),
                            GenresEntity(nameId = R.string.genre_romantic),
                            GenresEntity(nameId = R.string.genre_party),
                            GenresEntity(nameId = R.string.genre_concentrate),
                            GenresEntity(nameId = R.string.genre_blues),
                            GenresEntity(nameId = R.string.genre_indie),
                            GenresEntity(nameId = R.string.genre_jazz),
                            GenresEntity(nameId = R.string.genre_lating),
                            GenresEntity(nameId = R.string.genre_edm),
                            GenresEntity(nameId = R.string.genre_v_pop),
                            GenresEntity(nameId = R.string.genre_pop),
                            GenresEntity(nameId = R.string.genre_r_and_b),
                            GenresEntity(nameId = R.string.genre_rook))

    return genresData
}