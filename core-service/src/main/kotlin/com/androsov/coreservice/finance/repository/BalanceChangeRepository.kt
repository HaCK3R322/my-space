package com.androsov.coreservice.finance.repository

import com.androsov.coreservice.finance.model.entity.BalanceChange
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BalanceChangeRepository : CrudRepository<BalanceChange, UUID> {
    fun findAllByBalanceId(balanceId: UUID?): List<BalanceChange>
}