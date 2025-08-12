package com.clone.youtube.domain.user.repository

import com.clone.youtube.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun findByClerkId(userId: String): User?
}