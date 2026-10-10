package com.androsov.coreservice.finance.repository

import com.androsov.coreservice.finance.model.entity.BalanceChangeEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BalanceChangeRepository : CrudRepository<BalanceChangeEntity, UUID> {
    fun findAllByBalanceId(balanceId: UUID?): List<BalanceChangeEntity>
}