package com.live2ver.web.domain.user.entity

import com.live2ver.web.domain.comment.entity.Comment
import com.live2ver.web.domain.comment.entity.CommentReaction
import com.live2ver.web.domain.playlist.entity.Playlist
import com.live2ver.web.domain.video.entity.Subscription
import com.live2ver.web.domain.video.entity.Video
import com.live2ver.web.domain.video.entity.VideoReaction
import com.live2ver.web.domain.video.entity.VideoView
import com.live2ver.web.global.common.uuid.UuidManager
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

    @Column(name = "IMG_URL")
    val imageUrl: String? = null,

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