package com.androsov.coreservice.finance.model.inner

import com.androsov.coreservice.finance.model.entity.Balance as BalanceEntity
import com.androsov.coreservice.finance.model.entity.BalanceChange
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class Balance(
    val id: UUID,
    val name: String,
    val balance: BigDecimal,
    val changes: List<Change>,
) {
    data class Change(
        val dateTime: LocalDateTime,
        val change: BigDecimal,
        val title: String,
        val balanceFrom: UUID? = null,
        val balanceTo: UUID? = null,
    ) {
        companion object {
            fun from(entity: BalanceChange) =
                Change(
                    dateTime = entity.dateTime,
                    change = entity.change,
                    title = entity.title,
                    balanceFrom = entity.balanceFrom,
                    balanceTo = entity.balanceTo,
                )
        }
    }

    companion object {
        fun from(
            entity: BalanceEntity,
            balanceChangeEntities: List<BalanceChange>,
        ): Balance {
            val changes =
                balanceChangeEntities
                    .filter { it.balanceId == entity.id }
                    .map { Change.from(it) }
                    .sortedBy { it.dateTime }

            val balance =
                changes.fold(BigDecimal.ZERO) { acc, change -> acc + change.change }

            return Balance(
                id = entity.id,
                name = entity.name,
                balance = balance,
                changes = changes,
            )
        }
    }
}