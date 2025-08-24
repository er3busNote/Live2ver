package com.live2ver.web.domain.playlist.entity

import com.live2ver.web.domain.video.entity.Video
import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(
    name = "PLAYLIST_VIDEO",
    indexes = [
        Index(name = "FK_PLAYLIST_TO_PLAYLIST_VIDEO", columnList = "PLST_ID"),
        Index(name = "FK_VIDEO_TO_PLAYLIST_VIDEO", columnList = "VDO_ID")
    ]
)
data class PlaylistVideo(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLST_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_PLAYLIST_TO_PLAYLIST_VIDEO"))
    val playlist: Playlist? = null,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VDO_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_VIDEO_TO_PLAYLIST_VIDEO"))
    val video: Video? = null,

    @Column(name = "CRT_AT", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPD_AT", nullable = false)
    val updatedAt: Instant = Instant.now()
) : Serializable
