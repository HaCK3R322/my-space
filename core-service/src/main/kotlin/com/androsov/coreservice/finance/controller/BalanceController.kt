package com.androsov.coreservice.finance.controller

import com.androsov.coreservice.finance.model.dto.BalanceCreateRequestDto
import com.androsov.coreservice.finance.model.dto.BalanceCreateResponseDto
import com.androsov.coreservice.finance.model.dto.BalanceDto
import com.androsov.coreservice.finance.model.dto.change.CreateBalanceChangeRequestDto
import com.androsov.coreservice.finance.service.BalanceService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class BalanceController(
    private val balanceService: BalanceService
) {
    @PostMapping("/balances")
    fun addBalance(@RequestBody balanceCreateRequestDto: BalanceCreateRequestDto): BalanceCreateResponseDto {
        val createdBalance =
            balanceService.createBalance(
                request = balanceCreateRequestDto
            )

        return BalanceCreateResponseDto.from(createdBalance)
    }

    @GetMapping("/balances")
    fun getAllBalances(): List<BalanceDto> {
        return balanceService.getAllBalances().map { BalanceDto.from(it) }
    }

    @GetMapping("/balances/{id}")
    fun getBalanceById(@PathVariable id: UUID): BalanceDto = BalanceDto.from(balanceService.getBalanceById(id))

    @DeleteMapping("/balances")
    fun deleteAllBalances() = balanceService.deleteAllBalances()

    @PostMapping("/balances/{balanceId}/changes")
    fun addChange(
        @PathVariable balanceId: UUID,
        @RequestBody createBalanceChangeRequestDto: CreateBalanceChangeRequestDto,
    ): BalanceDto = BalanceDto.from(balanceService.addChange(balanceId, createBalanceChangeRequestDto))
}