package com.clone.youtube.domain.user.entity

import com.clone.youtube.domain.comment.entity.Comment
import com.clone.youtube.domain.comment.entity.CommentReaction
import com.clone.youtube.domain.playlist.entity.Playlist
import com.clone.youtube.domain.video.entity.Subscription
import com.clone.youtube.domain.video.entity.Video
import com.clone.youtube.domain.video.entity.VideoReaction
import com.clone.youtube.domain.video.entity.VideoView
import com.clone.youtube.global.common.uuid.UuidManager
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.Instant

@Entity
@Table(
    name = "USER",
    uniqueConstraints = [UniqueConstraint(name = "UK_ID", columnNames = ["ID"])]
)
data class User (
    @Id
    @Column(name = "US_ID", length = 32)
    var id: String? = null,

    @Column(name = "ID", nullable = false, unique = true)
    val username: String,

    @Column(name = "PWD", nullable = false)
    val password: String,

    @Column(name = "NM", nullable = false)
    val name: String,

    @Column(name = "BNNR_URL")
    val bannerUrl: String? = null,

    @Column(name = "BNNR_KEY")
    val bannerKey: String? = null,

    @Column(name = "IMG_URL", nullable = false)
    val imageUrl: String,

    @Column(name = "CRT_AT", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "UPD_AT", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val playlists: List<Playlist> = emptyList(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val videos: List<Video> = emptyList(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: List<Comment> = emptyList(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val videoViews: List<VideoView> = emptyList(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val videoReactions: List<VideoReaction> = emptyList(),

    @OneToMany(mappedBy = "viewer", cascade = [CascadeType.ALL], orphanRemoval = true)
    val subscriptions: List<Subscription> = emptyList(),

    @OneToMany(mappedBy = "creator", cascade = [CascadeType.ALL], orphanRemoval = true)
    val subscribers: List<Subscription> = emptyList(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val commentReactions: List<CommentReaction> = emptyList()
) {
    @PrePersist
    fun generateUserId() {
        if (id == null) {
            id = UuidManager.generateId()
        }
        createdAt = Instant.now()
        updatedAt = Instant.now()
    }

    @PreUpdate
    fun updateUser() {
        updatedAt = Instant.now()
    }
}