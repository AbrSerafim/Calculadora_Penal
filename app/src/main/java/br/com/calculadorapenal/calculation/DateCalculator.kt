package br.com.calculadorapenal.calculation

import java.time.LocalDate

object DateCalculator {

    fun addFractionOfPenalty(
        baseDate: LocalDate,
        penalty: PenaltyDuration,
        percentage: Double
    ): LocalDate {

        val totalDays =
            penalty.years * 365L +
                    penalty.months * 30L +
                    penalty.days

        val requiredDays = (totalDays * percentage).toLong()

        return baseDate.plusDays(requiredDays)
    }
}