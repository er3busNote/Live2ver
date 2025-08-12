package com.clone.youtube.domain.playlist.entity

import com.clone.youtube.domain.user.entity.User
import com.clone.youtube.global.common.uuid.UuidManager
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "PLAYLIST",
    indexes = [
        Index(name = "FK_USER_TO_PLAYLIST", columnList = "US_ID")
    ]
)
data class Playlist(
    @Id
    @Column(name = "PLST_ID", length = 32)
    var id: String? = null,

    @Column(name = "NM", nullable = false)
    val name: String,

    @Column(name = "DSC", nullable = false)
    val description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "US_ID", nullable = false, foreignKey = ForeignKey(name = "FK_USER_TO_PLAYLIST"))
    val user: User,

    @Column(name = "CRT_AT", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "UPD_AT", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @OneToMany(mappedBy = "playlist", cascade = [CascadeType.ALL], orphanRemoval = true)
    val playlistVideos: List<PlaylistVideo> = emptyList()
) {
    @PrePersist
    fun generatePlaylistId() {
        if (id == null) {
            id = UuidManager.generateId()
        }
        createdAt = Instant.now()
        updatedAt = Instant.now()
    }

    @PreUpdate
    fun updatePlaylist() {
        updatedAt = Instant.now()
    }
}
