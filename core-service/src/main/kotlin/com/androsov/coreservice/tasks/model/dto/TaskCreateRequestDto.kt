package com.androsov.coreservice.tasks.model.dto

import java.sql.Time
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.util.Date

data class TaskCreateRequestDto(
    val description: String,

    // Шаблон-параметры для описания времени выполнения задачи
    val daysOfWeekRepeat: Set<DayOfWeek>? = null,
    val daysOfMonthRepeat: Set<Int>? = null,
    val everyNDaysRepeat: Int? = null,

    val time: Time? = null,
    val startTime: Time? = null,
    val durationInMinutes: Long? = null,

    // Диапазон дат, среди которых существует задача
    val firstDay: LocalDate,
    val lastDay: LocalDate? = null,
) {
    fun validate() {
        var repeatTypesCount = 0;
        if (daysOfWeekRepeat != null) repeatTypesCount++
        if (daysOfMonthRepeat != null) repeatTypesCount++
        if (everyNDaysRepeat != null) repeatTypesCount++

        if(repeatTypesCount > 1) throw IllegalArgumentException("Too many repeat types: $repeatTypesCount")

        if (time!= null && startTime !=null) throw IllegalArgumentException("Task cannot have both time and startTime")
        if (startTime != null && durationInMinutes == null) throw IllegalArgumentException("If task has startTime it must have durationInMinutes")

        if (lastDay != null && lastDay < firstDay) throw IllegalArgumentException("lastDay cannot be before firstDay")
    }
}