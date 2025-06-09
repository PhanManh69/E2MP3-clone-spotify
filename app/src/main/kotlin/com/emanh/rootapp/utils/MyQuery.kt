package com.emanh.rootapp.utils

import com.emanh.rootapp.utils.MyConstant.ALBUMS_SEARCH
import com.emanh.rootapp.utils.MyConstant.ARTISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.PLAYLISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.SONGS_SEARCH

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

    const val QUERY_QUICK_ALBUM = """
        SELECT *
        FROM albums
        ORDER BY RANDOM()
        LIMIT 2
    """

    const val QUERY_YOUR_TOP_MIXES = """
        SELECT *
        FROM playlists
        WHERE isRadio = false AND owner_id = 1
    """

    const val QUERY_RADIO_FOR_YOU = """
        SELECT *
        FROM playlists
        WHERE isRadio = true
    """

    const val QUERY_PLAYLIST_CARD = """
        SELECT *
        FROM playlists
        WHERE isRadio = false
        ORDER BY RANDOM()
        LIMIT 6
    """

    const val QUERY_YOUR_FAVORITE_ARTISTS = """
        SELECT * 
        FROM users u
        JOIN cross_ref_song_artist sa ON sa.songId = (
            SELECT COALESCE(
                (
                    SELECT s.songId
                    FROM songs s
                    JOIN views_song vs ON s.songId = vs.song_id
                    WHERE vs.user_id = :userId
                    ORDER BY vs.number_listener DESC
                    LIMIT 1
                ),
                (
                    SELECT s2.songId
                    FROM songs s2
                    ORDER BY RANDOM()
                    LIMIT 1
                )
            )
        )
        WHERE u.userId = sa.userId
        ORDER BY RANDOM()
        LIMIT 1
    """

    const val QUERY_SIMILAR_ARTISTS = """
        SELECT *
        FROM users
        WHERE is_artist = true
            AND userId NOT IN (
                SELECT u.userId
                FROM users u
                JOIN cross_ref_song_artist sa ON u.userId = sa.userId
                WHERE sa.songId = (
                    SELECT COALESCE(
                        (
                            SELECT s.songId
                            FROM songs s
                            JOIN views_song vs ON s.songId = vs.song_id
                            WHERE vs.user_id = :userId
                            ORDER BY vs.number_listener DESC
                            LIMIT 1
                        ),
                        (
                            SELECT s2.songId
                            FROM songs s2
                            ORDER BY RANDOM()
                            LIMIT 1
                        )
                    )
                )
        )
        ORDER BY RANDOM()
        LIMIT 6
    """

    const val QUERY_SIMILAR_SONGS = """
        SELECT *
        FROM songs
        ORDER BY RANDOM()
        LIMIT 2
    """

    const val QUERY_SIMILAR_ALBUMS = """
        SELECT *
        FROM albums
        ORDER BY RANDOM()
        LIMIT 2
    """

    const val QUERY_GET_PLAYLIST_SONGS = """
        SELECT *
        FROM playlists
        WHERE playlistId = :playlistId
    """

    const val QUERY_GET_ALBUM_SONGS = """
        SELECT *
        FROM albums
        WHERE albumId = :albumId
    """

    const val QUERY_OWNER_PLAYLIST = """
        SELECT *
        FROM users
        WHERE userId = :userId
    """

    const val QUERY_OWNER_ALBUM = """
        SELECT *
        FROM users
        WHERE userId IN (
            SELECT aa.userId
            FROM albums a
            JOIN cross_ref_album_artist aa ON a.albumId = aa.albumId
            WHERE a.albumId = :albumId
        )
    """

    const val QUERY_TOTAL_LISTENER_ALBUM = """
        SELECT SUM(v.number_listener) AS total_views
        FROM views_song v
        WHERE v.song_id IN (
            SELECT s.songId
            FROM songs s
            JOIN cross_ref_album_song _as ON s.songId = _as.songId
            JOIN albums a ON _as.albumId = a.albumId
            WHERE a.albumId = :albumId
        )
    """

    const val QUERY_SONG_BY_ID = """
        SELECT *
        FROM songs
        WHERE songId = :songId
    """

    const val QUERY_MORE_BY_ARTISTS = """
        SELECT DISTINCT s.*
        FROM songs s
        JOIN cross_ref_song_artist sa ON s.songId = sa.songId
        WHERE sa.userId IN (
            SELECT userId
            FROM cross_ref_song_artist
            WHERE songId = :songId
        )
        ORDER BY RANDOM()
        LIMIT 10
    """

    const val QUERY_ARTIST_BY_ID = """
        SELECT *
        FROM users
        WHERE userId = :userId
    """

    const val QUERY_SONGS_BY_ARTIST = """
        SELECT s.*
        FROM songs s
        JOIN cross_ref_song_artist sa ON s.songId = sa.songId
        WHERE sa.userId = :userId
        ORDER BY RANDOM()
        LIMIT 10
    """

    const val QUERY_GENRE_NAME_BY_ARTIST = """
        SELECT DISTINCT g.name
        FROM genres g
        JOIN cross_ref_song_genre sg ON g.genreId = sg.genreId
        JOIN songs s ON sg.songId = s.songId
        JOIN cross_ref_song_artist sa ON s.songId = sa.songId
        WHERE sa.userId = :userId
    """

    const val QUERY_LISTENER_MONTH = """
        SELECT SUM(v.number_listener) AS total_views
        FROM views_song v
        WHERE v.song_id IN (
            SELECT s.songId
            FROM songs s
            JOIN cross_ref_song_artist sa ON s.songId = sa.songId
            JOIN users u ON sa.userId = u.userId
            WHERE u.userId = :userId
        )
        AND substr(v.date_time, 7, 4) || '-' || substr(v.date_time, 4, 2) = strftime('%Y-%m', 'now')
    """

    const val QERRY_SEARCH_SONGS = """
        SELECT *
        FROM songs
        WHERE LOWER(normalized_search_value) LIKE '%' || LOWER(:value) || '%'
        ORDER BY 
            CASE 
                WHEN LOWER(normalized_search_value) LIKE LOWER(:value) || '%' THEN 0
                ELSE 1
            END,
            RANDOM()
        LIMIT 10
    """

    const val QERRY_SEARCH_ARTISTS = """
        SELECT *
        FROM users
        WHERE LOWER(normalized_search_value) LIKE '%' || LOWER(:value) || '%' AND is_artist = 1
        ORDER BY 
            CASE 
                WHEN LOWER(normalized_search_value) LIKE LOWER(:value) || '%' THEN 0
                ELSE 1
            END,
            RANDOM()
        LIMIT 10
    """

    const val QERRY_SEARCH_ALBUMS = """
        SELECT *
        FROM albums
        WHERE LOWER(normalized_search_value) LIKE '%' || LOWER(:value) || '%'
        ORDER BY 
            CASE 
                WHEN LOWER(normalized_search_value) LIKE LOWER(:value) || '%' THEN 0
                ELSE 1
            END,
            RANDOM()
        LIMIT 10
    """

    const val QERRY_SEARCH_PLAYLISTS = """
        SELECT *
        FROM playlists
        WHERE LOWER(normalized_search_value) LIKE '%' || LOWER(:value) || '%'
        ORDER BY 
            CASE 
                WHEN LOWER(normalized_search_value) LIKE LOWER(:value) || '%' THEN 0
                ELSE 1
            END,
            RANDOM()
        LIMIT 10
    """

    const val QERRY_SEARCH = """
        SELECT *
        FROM search
        WHERE LOWER(normalized_search_value) LIKE '%' || LOWER(:value) || '%'
        ORDER BY 
            CASE 
                WHEN LOWER(normalized_search_value) LIKE LOWER(:value) || '%' THEN 0
                ELSE 1
            END,
            RANDOM()
        LIMIT 10
    """

    const val QUERY_GET_SONGS_BY_SEARCH = """
        SELECT songs.*
        FROM songs
        JOIN search ON songs.songId = search.idTable
        WHERE search.isTable = "$SONGS_SEARCH" AND search.idTable IN (:listId)
    """

    const val QUERY_GET_ARTISTS_BY_SEARCH = """
        SELECT users.*
        FROM users
        JOIN search ON users.userId = search.idTable
        WHERE search.isTable = "$ARTISTS_SEARCH" AND search.idTable IN (:listId)
    """

    const val QUERY_GET_ALBUMS_BY_SEARCH = """
        SELECT albums.*
        FROM albums
        JOIN search ON albums.albumId = search.idTable
        WHERE search.isTable = "$ALBUMS_SEARCH" AND search.idTable IN (:listId)
    """

    const val QUERY_GET_PLAYLISTS_BY_SEARCH = """
        SELECT playlists.*
        FROM playlists
        JOIN search ON playlists.playlistId = search.idTable
        WHERE search.isTable = "$PLAYLISTS_SEARCH" AND search.idTable IN (:listId)
    """

    const val QUERY_GET_SEARCH_HISTORY = """
        SELECT *
        FROM search_history
        WHERE user_id = :userId
        ORDER BY searchHistoryId DESC
    """

    const val QUERY_GET_ALBUMS_BY_ID = """
        SELECT *
        FROM albums
        WHERE albumId = :albumId
    """

    const val QUERY_GET_PLAYLIST_BY_ID = """
        SELECT *
        FROM playlists
        WHERE playlistId = :playlistId
    """

    const val QUERY_DELETE_DUPLICATE = """
        DELETE FROM search_history
        WHERE user_id = :userId AND table_id = :tableId AND type = :type
    """

    const val QUERY_DELETE_SONG_LIKE = """
        DELETE FROM cross_ref_song_like WHERE songId = :songId AND userId = :userId
    """

    const val QUERY_GET_SONG_LIKE = """
        SELECT * FROM cross_ref_song_like WHERE songId = :songId AND userId = :userId
    """

    const val QUERY_DELETE_USER_FOLLOWING = """
        DELETE FROM cross_ref_user_following WHERE userId = :userId AND artistId = :artistId
    """

    const val QUERY_GET_USER_FOLLWING = """
        SELECT * FROM cross_ref_user_following WHERE userId = :userId AND artistId = :artistId
    """

    const val QUETY_GET_LIKED_SONGS_BY_USER = """
        SELECT s.*
        FROM songs s
        JOIN cross_ref_song_like sl ON s.songId = sl.songId
        WHERE sl.userId = :userId
    """

    const val QUETY_GET_PLAYLISTS_YOUR_BY_USER = """
        SELECT *
        FROM playlists
        WHERE owner_id = :userId
    """

    const val QUETY_GET_PLAYLISTS_FOR_YOU_BY_USER = """
        SELECT p.*
        FROM playlists p
        JOIN cross_ref_playlist_like pl ON p.playlistId = pl.playlistId
        WHERE pl.userId = :userId
    """

    const val QUETY_GET_FOVERITE_ARISTS_BY_USER = """
        SELECT u.*
        FROM users u
        JOIN cross_ref_user_following uf ON u.userId = uf.artistId
        WHERE uf.userId = :userId
    """

    const val QUETY_GET_ALBUM_LIKE_BY_USER = """
        SELECT *
        FROM albums a
        JOIN cross_ref_album_like al ON a.albumId = al.albumId
        WHERE al.userId = :userId
    """

    const val QUERY_GET_PLAYLIST_LIKE = """
        SELECT * FROM cross_ref_playlist_like WHERE playlistId = :playlistId AND userId = :userId
    """

    const val QUERY_DELETE_PLAYLIST_LIKE = """
        DELETE FROM cross_ref_playlist_like WHERE playlistId = :playlistId AND userId = :userId
    """

    const val QUERY_GET_ALBUM_LIKE = """
        SELECT * FROM cross_ref_album_like WHERE albumId = :albumId AND userId = :userId
    """

    const val QUERY_DELETE_ALBUM_LIKE = """
        DELETE FROM cross_ref_album_like WHERE albumId = :albumId AND userId = :userId
    """

    const val QUERY_GET_OWNER_ALBUM = """
        SELECT u.*
        FROM users u
        JOIN playlists p ON u.userId = p.owner_id
        WHERE p.playlistId = :playlistId
    """

    const val QUERY_GET_SONGS_RECOMMEND = """
        SELECT *
        FROM songs s
        JOIN cross_ref_song_genre sg ON s.songId = sg.songId
        WHERE sg.genreId = 1 OR sg.genreId = 12
            AND s.songId NOT IN (
                SELECT songId
                FROM cross_ref_playlist_song
                WHERE playlistId = :albumId
            )
        ORDER BY RANDOM()
        LIMIT 5
    """

    const val QUERY_GET_RANDOM_SONG_EXCLUDING = """
        SELECT * 
        FROM songs
        WHERE songId NOT IN (:excludeIds)
        ORDER BY RANDOM()
        LIMIT 1
    """

    const val QUERY_GET_USER_LOGIN = """
        SELECT * FROM users 
        WHERE 
            (
                LOWER(username) = LOWER(:account)
                OR email = :account
            )
            AND password = :password
    """

    const val QUERY_GET_USERNAME = """
        SELECT *
        FROM users
        WHERE LOWER(username) = LOWER(:username)
    """

    const val QUERY_GET_EMAIL = """
        SELECT *
        FROM users
        WHERE LOWER(email) = LOWER(:email)
    """
}