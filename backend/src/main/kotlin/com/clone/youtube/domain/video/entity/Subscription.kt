package com.clone.youtube.domain.video.entity

import com.clone.youtube.domain.user.entity.User
import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(
    name = "SUBSCRIPTION",
    indexes = [
        Index(name = "FK_VIEWER_TO_SUBSCRIPTION", columnList = "VW_ID"),
        Index(name = "FK_CREATOR_TO_SUBSCRIPTION", columnList = "CRT_ID")
    ]
)
data class Subscription(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VW_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_VIEWER_TO_SUBSCRIPTION"))
    val viewer: User? = null,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRT_ID", insertable = false, updatable = false, foreignKey = ForeignKey(name = "FK_CREATOR_TO_SUBSCRIPTION"))
    val creator: User? = null,

    @Column(name = "CRT_AT", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "UPD_AT", nullable = false)
    val updatedAt: Instant = Instant.now()
) : Serializable
