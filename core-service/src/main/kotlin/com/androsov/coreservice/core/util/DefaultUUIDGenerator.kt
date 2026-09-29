package com.androsov.coreservice.core.util

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DefaultUUIDGenerator : UUIDGenerator {
    override fun generate(): UUID {
        return UUID.randomUUID()
    }
}
