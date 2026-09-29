package com.androsov.coreservice.tasks.model.entity

import com.androsov.coreservice.tasks.model.dto.TaskCreateRequestDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Time
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "tasks")
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    // Полезная информация
    val description: String,

    // Шаблон-параметры для описания времени выполнения задачи
    val daysOfWeekRepeat: Set<DayOfWeek>? = null,
    val daysOfMonthRepeat: Set<Int>? = null,
    val everyNDaysRepeat: Int? = null,

    val time: Time? = null,
    val startTime: Time? = null,
    val durationInMinutes: Long? = null,

    // Диапазон дат, среди которых существует задача
    val firstDay: LocalDate? = null,
    val lastDay: LocalDate? = null
) {
    companion object {
        fun from(requestDto: TaskCreateRequestDto) =
            TaskEntity(
                description = requestDto.description,
                daysOfWeekRepeat = requestDto.daysOfWeekRepeat,
                daysOfMonthRepeat = requestDto.daysOfMonthRepeat,
                everyNDaysRepeat = requestDto.everyNDaysRepeat,
                time = requestDto.time,
                startTime = requestDto.startTime,
                durationInMinutes = requestDto.durationInMinutes,
                firstDay = requestDto.firstDay,
                lastDay = requestDto.lastDay
            )
    }
}
