package com.clone.youtube.domain.auth.dto.request

data class RefreshRequest(val userId: String, val refreshToken: String)
