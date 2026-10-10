package com.androsov.coreservice.finance.model.dto

import com.androsov.coreservice.finance.model.inner.Balance

data class BalanceCreateResponseDto(
    val data: BalanceDto,
) {
    companion object {
        fun from(balance: Balance) =
            BalanceCreateResponseDto(
                data = BalanceDto.from(balance),
            )
    }
}
