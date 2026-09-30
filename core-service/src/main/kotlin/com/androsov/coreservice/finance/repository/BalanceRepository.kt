package com.androsov.coreservice.finance.repository

import com.androsov.coreservice.finance.model.entity.BalanceEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BalanceRepository : CrudRepository<BalanceEntity, UUID>