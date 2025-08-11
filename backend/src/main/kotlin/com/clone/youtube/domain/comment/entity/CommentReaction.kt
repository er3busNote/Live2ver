package com.clone.youtube.domain.comment.entity

import com.clone.youtube.domain.user.entity.User
import com.clone.youtube.domain.video.type.ReactionType
import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(
    name = "COMMENT_REACTION",
    indexes = [
        Index(name = "FK_USER_TO_COMMENT_REACTION", columnList = "USER_ID"),
        Index(name = "FK_COMMENT_TO_COMMENT_REACTION", columnList = "VIDEO_ID")
    ]
)
data class CommentReaction(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_USER_TO_COMMENT_REACTION"))
    val user: User? = null,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_COMMENT_TO_COMMENT_REACTION"))
    val comment: Comment? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: ReactionType,

    @Column(name = "CREATED_AT", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPDATED_AT", nullable = false)
    val updatedAt: Instant = Instant.now()
) : Serializable
