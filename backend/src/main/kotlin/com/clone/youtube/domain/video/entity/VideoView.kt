package com.clone.youtube.domain.video.entity

import com.clone.youtube.domain.user.entity.User
import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(
    name = "VIDEO_VIEW",
    indexes = [
        Index(name = "FK_USER_TO_VIDEO_VIEW", columnList = "USER_ID"),
        Index(name = "FK_VIDEO_TO_VIDEO_VIEW", columnList = "VIDEO_ID")
    ]
)
data class VideoView(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_USER_TO_VIDEO_VIEW"))
    val user: User? = null,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIDEO_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_VIDEO_TO_VIDEO_VIEW"))
    val video: Video? = null,

    @Column(name = "CREATED_AT", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPDATED_AT", nullable = false)
    val updatedAt: Instant = Instant.now()
) : Serializable
