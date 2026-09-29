package com.androsov.coreservice.finance.repository

import com.androsov.coreservice.finance.model.entity.Balance
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BalanceRepository : CrudRepository<Balance, UUID>