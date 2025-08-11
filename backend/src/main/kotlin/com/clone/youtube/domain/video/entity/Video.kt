package com.clone.youtube.domain.video.entity

import com.clone.youtube.domain.category.entity.Category
import com.clone.youtube.domain.comment.entity.Comment
import com.clone.youtube.domain.playlist.entity.PlaylistVideo
import com.clone.youtube.domain.user.entity.User
import com.clone.youtube.domain.video.type.VideoVisibility
import com.clone.youtube.global.common.uuid.UuidManager
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "VIDEO",
    indexes = [
        Index(name = "FK_USER_TO_VIDEO", columnList = "USER_ID"),
        Index(name = "FK_CATEGORY_TO_VIDEO", columnList = "CATEGORY_ID")
    ]
)
data class Video (
    @Id
    @Column(name = "VIDEO_ID", length = 32)
    var id: String? = null,

    @Column(name = "TITLE", nullable = false)
    val title: String,

    @Column(name = "DESCRIPTION", nullable = false)
    val description: String? = null,

    @Column(name = "MUX_STS")
    val muxStatus: String? = null,

    @Column(name = "MUX_ASSET_ID", unique = true)
    val muxAssetId: String? = null,

    @Column(name = "MUX_UPLOAD_ID", unique = true)
    val muxUploadId: String? = null,

    @Column(name = "MUX_PLAYBACK_ID", unique = true)
    val muxPlaybackId: String? = null,

    @Column(name = "MUX_TRACK_ID", unique = true)
    val muxTrackId: String? = null,

    @Column(name = "MUX_TRACK_STS")
    val muxTrackStatus: String? = null,

    @Column(name = "THUMBNAIL_URL")
    val thumbnailUrl: String? = null,

    @Column(name = "THUMBNAIL_KEY")
    val thumbnailKey: String? = null,

    @Column(name = "PREVIEW_URL")
    val previewUrl: String? = null,

    @Column(name = "PREVIEW_KEY")
    val previewKey: String? = null,

    @Column(name = "DURATION", nullable = false)
    val duration: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "VISIBILITY", nullable = false)
    val visibility: VideoVisibility = VideoVisibility.private,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = ForeignKey(name = "FK_USER_TO_VIDEO"))
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", foreignKey = ForeignKey(name = "FK_CATEGORY_TO_VIDEO"))
    val category: Category? = null,

    @Column(name = "CREATED_AT", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "UPDATED_AT", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @OneToMany(mappedBy = "video", cascade = [CascadeType.ALL], orphanRemoval = true)
    val videoViews: List<VideoView> = emptyList(),

    @OneToMany(mappedBy = "video", cascade = [CascadeType.ALL], orphanRemoval = true)
    val videoReactions: List<VideoReaction> = emptyList(),

    @OneToMany(mappedBy = "video", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: List<Comment> = emptyList(),

    @OneToMany(mappedBy = "video", cascade = [CascadeType.ALL], orphanRemoval = true)
    val playlistVideos: List<PlaylistVideo> = emptyList()
) {
    @PrePersist
    fun generateVideoId() {
        if (id == null) {
            id = UuidManager.generateId()
        }
        createdAt = Instant.now()
        updatedAt = Instant.now()
    }

    @PreUpdate
    fun updateVideo() {
        updatedAt = Instant.now()
    }
}