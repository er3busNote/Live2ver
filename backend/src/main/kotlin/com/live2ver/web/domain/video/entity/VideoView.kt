package com.live2ver.web.domain.video.entity

import com.live2ver.web.domain.user.entity.User
import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(
    name = "VIDEO_VIEW",
    indexes = [
        Index(name = "FK_USER_TO_VIDEO_VIEW", columnList = "US_ID"),
        Index(name = "FK_VIDEO_TO_VIDEO_VIEW", columnList = "VDO_ID")
    ]
)
data class VideoView(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "US_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_USER_TO_VIDEO_VIEW"))
    val user: User? = null,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VDO_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_VIDEO_TO_VIDEO_VIEW"))
    val video: Video? = null,

    @Column(name = "CRT_AT", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPD_AT", nullable = false)
    val updatedAt: Instant = Instant.now()
) : Serializable
