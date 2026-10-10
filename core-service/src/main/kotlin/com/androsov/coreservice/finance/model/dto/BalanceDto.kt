package com.androsov.coreservice.finance.model.dto

import com.androsov.coreservice.finance.model.inner.Balance
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class BalanceDto(
    val id: UUID,
    val name: String,
    val balance: BigDecimal,
    val changes: List<ChangeDto>,
) {
    data class ChangeDto(
        val dateTime: LocalDateTime,
        val change: BigDecimal,
        val title: String,
        val balanceFrom: UUID? = null,
        val balanceTo: UUID? = null,
    ) {
        companion object {
            fun from(model: Balance.Change) =
                ChangeDto(
                    dateTime = model.dateTime,
                    change = model.change,
                    title = model.title,
                    balanceFrom = model.balanceFrom,
                    balanceTo = model.balanceTo,
                )
        }
    }

    companion object {
        fun from(balance: Balance) =
            BalanceDto(
                id = balance.id,
                name = balance.name,
                balance = balance.balance,
                changes = balance.changes.map { ChangeDto.from(it) },
            )
    }
}