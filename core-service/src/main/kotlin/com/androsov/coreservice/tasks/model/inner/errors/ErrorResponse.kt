package com.androsov.coreservice.tasks.model.inner.errors

import java.time.Instant

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String?,
    val timestamp: Instant = Instant.now()
)
