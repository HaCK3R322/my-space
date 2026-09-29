package com.androsov.coreservice.core.util

import java.util.UUID

interface UUIDGenerator {
    fun generate(): UUID
}