package com.androsov.coreservice.finance.model.inner

import com.androsov.coreservice.finance.model.entity.BalanceChangeEntity
import com.androsov.coreservice.finance.model.entity.BalanceEntity
import com.androsov.coreservice.finance.model.enums.BalanceChangeType
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class Balance(
    val id: UUID,
    val name: String,
    val balance: BigDecimal,
    val changes: List<Change>,
) {
    data class Change(
        val id: UUID,
        val date: LocalDate,
        val order: Long,
        val amount: BigDecimal,
        val title: String,
        val type: BalanceChangeType,
        val balanceId: UUID? = null,
        val balanceFrom: UUID? = null,
        val balanceTo: UUID? = null,
    ) {
        companion object {
            fun from(entity: BalanceChangeEntity) =
                Change(
                    id = entity.id,
                    date = entity.date,
                    order = entity.order,
                    amount = entity.amount,
                    title = entity.title,
                    type = entity.type,
                    balanceId = entity.balanceId,
                    balanceFrom = entity.balanceFrom,
                    balanceTo = entity.balanceTo,
                )
        }
    }

    companion object {
        fun from(
            entity: BalanceEntity,
            balanceChangeEntityEntities: List<BalanceChangeEntity>,
        ): Balance {
            val changes =
                balanceChangeEntityEntities
                    .asSequence()
                    .filter { change ->
                        change.balanceId == entity.id || change.balanceFrom == entity.id || change.balanceTo == entity.id
                    }.map { Change.from(it) }
                    .groupBy { it.date }
                    .toList()
                    .sortedBy { (date, _) -> date }
                    .flatMap { (_, changes) ->
                        changes.sortedBy { it.order }
                    }.toList()

            val balance =
                changes.fold(BigDecimal.ZERO) { acc, change ->
                    when (change.type) {
                        BalanceChangeType.SET -> {
                            change.amount
                        }

                        BalanceChangeType.ADD -> {
                            acc + change.amount
                        }

                        BalanceChangeType.EXTRACT -> {
                            acc - change.amount
                        }

                        BalanceChangeType.MOVE -> {
                            when (entity.id) {
                                change.balanceTo -> acc + change.amount
                                change.balanceFrom -> acc - change.amount
                                else -> error("Cannot construct acc balance: ")
                            }
                        }
                    }
                }

            return Balance(
                id = entity.id,
                name = entity.name,
                balance = balance,
                changes = changes,
            )
        }
    }
}
