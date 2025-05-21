package com.emanh.rootapp.data.db.entity

import kotlinx.serialization.Serializable

@Serializable
data class MusixmatchResponse(
    val message: Message
)

@Serializable
data class Message(
    val header: Header,
    val body: Body,
)

@Serializable
data class Header(
    val status_code: Int,
    val execute_time: Double,
)

@Serializable
data class Body(
    val lyrics: Lyrics? = null,
)

@Serializable
data class Lyrics(
    val lyrics_id: Long,
    val explicit: Int,
    val lyrics_body: String,
    val lyrics_copyright: String,
    val updated_time: String,
)