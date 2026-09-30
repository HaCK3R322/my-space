package com.androsov.coreservice.finance.service

import com.androsov.coreservice.core.util.UUIDGenerator
import com.androsov.coreservice.finance.model.dto.BalanceCreateRequestDto
import com.androsov.coreservice.finance.model.dto.change.CreateBalanceChangeRequestDto
import com.androsov.coreservice.finance.model.entity.BalanceChangeEntity
import com.androsov.coreservice.finance.model.entity.BalanceEntity
import com.androsov.coreservice.finance.repository.BalanceChangeRepository
import com.androsov.coreservice.finance.repository.BalanceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID
import com.androsov.coreservice.finance.model.inner.Balance as BalanceModel

@Service
class BalanceService(
    private val uuidGenerator: UUIDGenerator,
    private val balanceRepository: BalanceRepository,
    private val balanceChangeRepository: BalanceChangeRepository,
) {
    fun createBalance(request: BalanceCreateRequestDto): BalanceModel {
        val balanceEntity =
            BalanceEntity(
                id = uuidGenerator.generate(),
                name = request.name,
            )

        val savedBalance = balanceRepository.save(balanceEntity)

        return BalanceModel.from(
            entity = savedBalance,
            balanceChangeEntityEntities = listOf(),
        )
    }

    fun getAllBalances(): List<BalanceModel> {
        val balanceEntityEntities: List<BalanceEntity> = balanceRepository.findAll().toList()

        val balanceChangeEntityEntities: List<BalanceChangeEntity> = balanceChangeRepository.findAll().toList()

        return balanceEntityEntities.map { balanceEntity ->
            BalanceModel.from(
                entity = balanceEntity,
                balanceChangeEntityEntities = balanceChangeEntityEntities,
            )
        }
    }

    fun getBalanceById(id: UUID): BalanceModel {
        val balanceEntity: BalanceEntity =
            balanceRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("Balance not found with id: $id")

        val balanceChangeEntityEntities: List<BalanceChangeEntity> =
            balanceChangeRepository.findAllByBalanceId(balanceEntity.id)

        return BalanceModel.from(
            entity = balanceEntity,
            balanceChangeEntityEntities = balanceChangeEntityEntities,
        )
    }

    fun deleteAllBalances() {
        balanceRepository.deleteAll()
    }

    fun createBalanceChange(request: CreateBalanceChangeRequestDto) {
        request.validate()

        val existingChanges = balanceChangeRepository.findAll()

        val requestDayChanges = existingChanges.filter { it.date == request.date }.sortedBy { it.order }

        val newBalanceChangeOrder = (requestDayChanges.firstOrNull()?.order ?: 0) + 1

        val balanceChangeEntity =
            BalanceChangeEntity(
                id = uuidGenerator.generate(),
                date = request.date,
                order = newBalanceChangeOrder,
                amount = request.amount,
                title = request.title,
                type = request.type,
                balanceId = request.balanceId,
                balanceFrom = request.balanceFrom,
                balanceTo = request.balanceTo,
            )

        balanceChangeRepository.save(balanceChangeEntity)
    }
}
