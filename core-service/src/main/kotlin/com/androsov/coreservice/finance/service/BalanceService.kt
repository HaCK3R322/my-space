package com.androsov.coreservice.finance.service

import com.androsov.coreservice.core.util.UUIDGenerator
import com.androsov.coreservice.finance.model.dto.BalanceCreateRequestDto
import com.androsov.coreservice.finance.model.dto.change.BalanceChangeRequest
import com.androsov.coreservice.finance.model.entity.Balance
import com.androsov.coreservice.finance.model.entity.BalanceChange
import com.androsov.coreservice.finance.model.inner.Balance as BalanceModel
import com.androsov.coreservice.finance.repository.BalanceChangeRepository
import com.androsov.coreservice.finance.repository.BalanceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BalanceService(
    private val uuidGenerator: UUIDGenerator,
    private val balanceRepository: BalanceRepository,
    private val balanceChangeRepository: BalanceChangeRepository,
) {
    fun createBalance(request: BalanceCreateRequestDto): BalanceModel {
        val balanceEntity =
            Balance(
                id = uuidGenerator.generate(),
                name = request.name,
            )

        val savedBalance = balanceRepository.save(balanceEntity)

        return BalanceModel.from(
            entity = savedBalance,
            balanceChangeEntities = listOf(),
        )
    }

    fun getAllBalances(): List<BalanceModel> {
        val balanceEntities: List<Balance> = balanceRepository.findAll().toList()

        val balanceChangeEntities: List<BalanceChange> = balanceChangeRepository.findAll().toList()

        return balanceEntities.map { balanceEntity ->
            BalanceModel.from(
                entity = balanceEntity,
                balanceChangeEntities = balanceChangeEntities,
            )
        }
    }

    fun getBalanceById(id: UUID): BalanceModel {
        val balanceEntity: Balance =
            balanceRepository.findByIdOrNull(id)
                ?: throw IllegalArgumentException("Balance not found with id: $id")

        val balanceChangeEntities: List<BalanceChange> =
            balanceChangeRepository.findAllByBalanceId(balanceEntity.id)

        return BalanceModel.from(
            entity = balanceEntity,
            balanceChangeEntities = balanceChangeEntities,
        )
    }

    fun deleteAllBalances() {
        balanceRepository.deleteAll()
    }

    fun addChange(
        balanceId: UUID,
        request: BalanceChangeRequest,
    ): BalanceModel {
        if (!balanceRepository.existsById(balanceId)) {
            throw IllegalArgumentException("Balance not found with id: $balanceId")
        }

        balanceChangeRepository.save(
            BalanceChange(
                id = uuidGenerator.generate(),
                dateTime = request.dateTime,
                change = request.change,
                title = request.title,
                balanceId = balanceId,
                balanceFrom = request.balanceFrom,
                balanceTo = request.balanceTo,
            ),
        )

        return getBalanceById(balanceId)
    }
}