package com.clone.youtube.domain.category.entity

import com.clone.youtube.domain.video.entity.Video
import com.clone.youtube.global.common.uuid.UuidManager
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "CATEGORY",
    uniqueConstraints = [UniqueConstraint(name = "UK_NM", columnNames = ["NM"])]
)
data class Category(
    @Id
    @Column(name = "CAT_ID", length = 32)
    var id: String? = null,

    @Column(name = "NM", nullable = false, unique = true)
    val name: String,

    @Column(name = "DSC", nullable = false)
    val description: String? = null,

    @Column(name = "CRT_AT", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "UPD_AT", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    val videos: List<Video> = emptyList()
) {
    @PrePersist
    fun generateCategoryId() {
        if (id == null) {
            id = UuidManager.generateId()
        }
        createdAt = Instant.now()
        updatedAt = Instant.now()
    }

    @PreUpdate
    fun updateCategory() {
        updatedAt = Instant.now()
    }
}
