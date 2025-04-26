package com.emanh.rootapp.utils

object MyQuery {
    const val QUERY_GENRE_BY_ID = """
        SELECT * FROM genres WHERE genreId = :genreId
    """

    const val QUERY_RECENTLY_LISTENED_SONGS = """
        SELECT s.* 
        FROM songs s 
        JOIN views_song vs ON s.songId = vs.song_id 
        WHERE vs.user_id = :userId
        ORDER BY datetime(substr(date_time, 7, 4) || '-' || substr(date_time, 4, 2) || '-' || substr(date_time, 1, 2) || ' ' || substr(date_time, 12)) DESC
    """

    const val QUERY_VIEW_RECORD = """
        SELECT * FROM views_song WHERE user_id = :userId AND song_id = :songId LIMIT 1
    """

    const val QUERY_SONGS_BY_GENRES = """
        SELECT s.*
        FROM songs s
        WHERE 
            -- Check if any genre ID in the song's genres list matches any ID in the provided genres list
            EXISTS (
                SELECT 1 
                FROM json_each(s.genresIdList) as sg
                WHERE instr(:genresList, sg.value) > 0
            )
        ORDER BY RANDOM()
        LIMIT :limit
    """
}