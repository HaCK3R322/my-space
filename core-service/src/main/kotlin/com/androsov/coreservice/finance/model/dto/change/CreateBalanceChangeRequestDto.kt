package com.androsov.coreservice.finance.model.dto.change

import com.androsov.coreservice.finance.model.enums.BalanceChangeType
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CreateBalanceChangeRequestDto(
    val date: LocalDate,
    val amount: BigDecimal,
    val title: String,
    val type: BalanceChangeType,
    val balanceId: UUID? = null,
    val balanceFrom: UUID? = null,
    val balanceTo: UUID? = null,
) {
    fun validate() {
        check(amount >= BigDecimal.ZERO) {
            "Amount always must be >= 0"
        }

        when (type) {
            BalanceChangeType.ADD, BalanceChangeType.EXTRACT, BalanceChangeType.SET -> {
                check(balanceId != null) {
                    "Balance change type is $type but balanceId is null"
                }
            }

            BalanceChangeType.MOVE -> {
                check(balanceFrom != null) {
                    "Balance change type is $type but balanceFrom is null"
                }

                check(balanceTo != null) {
                    "Balance change type is $type but balanceTo is null"
                }
            }
        }
    }
}
