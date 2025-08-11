package com.clone.youtube.domain.video.entity

import com.clone.youtube.domain.user.entity.User
import com.clone.youtube.domain.video.type.ReactionType
import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(
    name = "VIDEO_REACTION",
    indexes = [
        Index(name = "FK_USER_TO_VIDEO_REACTION", columnList = "USER_ID"),
        Index(name = "FK_VIDEO_TO_VIDEO_REACTION", columnList = "VIDEO_ID")
    ]
)
data class VideoReaction(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_USER_TO_VIDEO_REACTION"))
    val user: User? = null,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIDEO_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_VIDEO_TO_VIDEO_REACTION"))
    val video: Video? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: ReactionType,

    @Column(name = "CREATED_AT", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPDATED_AT", nullable = false)
    val updatedAt: Instant = Instant.now()
) : Serializable
