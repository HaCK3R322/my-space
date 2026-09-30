package com.androsov.coreservice.finance.model.entity

import com.androsov.coreservice.finance.model.enums.BalanceChangeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "balances_changes")
data class BalanceChangeEntity(
    @Id val id: UUID,
    val date: LocalDate,
    val order: Long,
    val amount: BigDecimal,
    val title: String,
    val type: BalanceChangeType,
    val balanceId: UUID? = null,
    val balanceFrom: UUID? = null,
    val balanceTo: UUID? = null,
)
