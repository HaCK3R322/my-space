package com.androsov.coreservice.finance.model.dto.change

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class BalanceChangeRequest(
    val dateTime: LocalDateTime,
    val change: BigDecimal,
    val title: String,
    val balanceFrom: UUID? = null,
    val balanceTo: UUID? = null,
)