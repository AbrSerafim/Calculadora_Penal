package br.com.calculadorapenal.calculation

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.floor

object DateCalculator {

    fun addFractionOfPenalty(
        baseDate: LocalDate,
        penalty: PenaltyDuration,
        percentage: Double
    ): LocalDate {

        // Calculate the end of the sentence using calendar dates
        val sentenceEnd = baseDate
            .plusYears(penalty.years.toLong())
            .plusMonths(penalty.months.toLong())
            .plusDays(penalty.days.toLong())

        // Number of days in the sentence
        val totalDays = ChronoUnit.DAYS.between(
            baseDate,
            sentenceEnd
        )

        // Required fraction of the sentence
        val requiredDays = floor(
            totalDays * percentage
        ).toLong()

        return baseDate.plusDays(requiredDays)
    }
}