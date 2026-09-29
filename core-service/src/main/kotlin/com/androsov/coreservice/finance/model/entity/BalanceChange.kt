package com.androsov.coreservice.finance.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "balances_changes")
data class BalanceChange(
    @Id val id: UUID,
    val dateTime: LocalDateTime,
    val change: BigDecimal,
    val title: String,
    val balanceId: UUID? = null,
    val balanceFrom: UUID? = null,
    val balanceTo: UUID? = null,
)
