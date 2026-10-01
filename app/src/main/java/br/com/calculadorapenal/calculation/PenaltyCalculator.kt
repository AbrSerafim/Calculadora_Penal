package br.com.calculadorapenal.calculation

import br.com.calculadorapenal.model.CalculationData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.floor

object PenaltyCalculator {

    private val dateFormatter =
        DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun calculate(data: CalculationData): CalculationResult {

        val startDate = LocalDate.parse(
            data.startDate,
            dateFormatter
        )

        // Apply detraction before calculating any dates
        val baseDate = startDate.minusDays(
            data.detractionDays.toLong()
        )

        val penalty = data.penaltyDuration()

        // =====================================================
        // 1st progression: Closed -> Semi-open
        // =====================================================

        val progressionRule = RuleProvider.getProgressionRule(data)

        val semiOpenDate = DateCalculator.addFractionOfPenalty(
            baseDate = baseDate,
            penalty = penalty,
            percentage = progressionRule.percentage
        )

        // =====================================================
        // 2nd progression: Semi-open -> Open
        // =====================================================

        val sentenceEnd = baseDate
            .plusYears(penalty.years.toLong())
            .plusMonths(penalty.months.toLong())
            .plusDays(penalty.days.toLong())

        val totalDays = ChronoUnit.DAYS.between(
            baseDate,
            sentenceEnd
        )

        val daysUntilSemiOpen = ChronoUnit.DAYS.between(
            baseDate,
            semiOpenDate
        )

        val remainingDays = totalDays - daysUntilSemiOpen

        val openRequiredDays = floor(
            remainingDays * progressionRule.percentage
        ).toLong()

        val openDate = semiOpenDate.plusDays(
            openRequiredDays
        )

        // =====================================================
        // Livramento condicional
        // =====================================================

        val paroleRule = ParoleRuleProvider.getParoleRule(data)

        val paroleDate = DateCalculator.addFractionOfPenalty(
            baseDate = baseDate,
            penalty = penalty,
            percentage = paroleRule.percentage
        )

        return CalculationResult(
            semiOpenDate = semiOpenDate.format(dateFormatter),
            openDate = openDate.format(dateFormatter),
            paroleDate = paroleDate.format(dateFormatter)
        )
    }
}