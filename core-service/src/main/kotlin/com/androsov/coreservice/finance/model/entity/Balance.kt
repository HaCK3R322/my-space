package com.androsov.coreservice.finance.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "balances")
data class Balance(
    @Id val id: UUID,
    val name: String,
)
