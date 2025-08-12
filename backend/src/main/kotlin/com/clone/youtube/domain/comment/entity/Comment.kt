package com.clone.youtube.domain.comment.entity

import com.clone.youtube.domain.user.entity.User
import com.clone.youtube.domain.video.entity.Video
import com.clone.youtube.global.common.uuid.UuidManager
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "COMMENT",
    indexes = [
        Index(name = "FK_USER_TO_COMMENT", columnList = "US_ID"),
        Index(name = "FK_VIDEO_TO_COMMENT", columnList = "VDO_ID")
    ]
)
data class Comment(
    @Id
    @Column(name = "CMT_ID", length = 32)
    var id: String? = null,

    @Column(name = "PRT_ID", length = 32)
    val parentId: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "US_ID", nullable = false, foreignKey = ForeignKey(name = "FK_USER_TO_COMMENT"))
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VDO_ID", nullable = false, foreignKey = ForeignKey(name = "FK_VIDEO_TO_COMMENT"))
    val video: Video,

    @Column(name = "VAL", nullable = false)
    val value: String,

    @Column(name = "CRT_AT", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "UPD_AT", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRT_ID", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    val parent: Comment? = null,

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    val replies: List<Comment> = emptyList(),

    @OneToMany(mappedBy = "comment", cascade = [CascadeType.ALL], orphanRemoval = true)
    val reactions: List<CommentReaction> = emptyList()
) {
    @PrePersist
    fun generateCommentId() {
        if (id == null) {
            id = UuidManager.generateId()
        }
        createdAt = Instant.now()
        updatedAt = Instant.now()
    }

    @PreUpdate
    fun updateComment() {
        updatedAt = Instant.now()
    }
}
