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

    const val QUERY_RECOMMENDED_YOUR = """
        WITH top_genres AS (
            SELECT sg.genreId
            FROM cross_ref_song_genre sg
            JOIN views_song vs ON sg.songId = vs.song_id
            WHERE vs.user_id = :userId
            GROUP BY sg.genreId
            ORDER BY COUNT(*) DESC
            LIMIT 3
        ),
        fallback_genres AS (
            SELECT genreId FROM (
                SELECT 1 AS genreId
                UNION ALL
                SELECT 12
                UNION ALL
                SELECT 14
            )
        ),
        selected_genres AS (
            SELECT genreId FROM top_genres
            UNION ALL
            SELECT genreId FROM fallback_genres
            WHERE NOT EXISTS (SELECT 1 FROM top_genres LIMIT 3)
            LIMIT 3
        )
        SELECT DISTINCT s.*
        FROM songs s
        JOIN cross_ref_song_genre sg1 ON s.songId = sg1.songId
        JOIN cross_ref_song_genre sg2 ON s.songId = sg2.songId
        WHERE sg1.genreId IN (SELECT genreId FROM selected_genres)
        AND sg2.genreId IN (SELECT genreId FROM selected_genres)
        AND sg1.genreId < sg2.genreId
        ORDER BY RANDOM()
        LIMIT 10
    """

    const val QUERY_TRENDING = """
        SELECT s.*
        FROM songs s
        JOIN views_song vs ON s.songId = vs.song_id
        ORDER BY number_listener DESC
        LIMIT 10
    """

    const val QUERY_QUICK_PLAYLIST = """
        SELECT *
        FROM playlists
        WHERE isRadio = false AND owner_id = 1 OR owner_id = :userId
        ORDER BY RANDOM()
        LIMIT 6
    """
}