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

        val baseDate = startDate.minusDays(
            data.detractionDays.toLong()
        )

        // Progression rule
        val progressionRule = RuleProvider.getProgressionRule(data)

        /*
         * 1st progression:
         * Closed -> Semi-open
         */
        val semiOpenDate = DateCalculator.addFractionOfPenalty(
            baseDate = baseDate,
            penalty = data.penaltyDuration(),
            percentage = progressionRule.percentage
        )

        /*
         * 2nd progression:
         * Semi-open -> Open
         *
         * The percentage is applied to the
         * remaining sentence.
         */

        // Calculate total sentence in days
        val sentenceEnd = baseDate
            .plusYears(data.penaltyDuration().years.toLong())
            .plusMonths(data.penaltyDuration().months.toLong())
            .plusDays(data.penaltyDuration().days.toLong())

        val totalDays = ChronoUnit.DAYS.between(
            baseDate,
            sentenceEnd
        )

        // Days already served when reaching semi-open
        val daysUntilSemiOpen = ChronoUnit.DAYS.between(
            baseDate,
            semiOpenDate
        )

        // Remaining sentence
        val remainingDays = totalDays - daysUntilSemiOpen

        // Required fraction of the remaining sentence
        val openRequiredDays = floor(
            remainingDays * progressionRule.percentage
        ).toLong()

        val openDate = semiOpenDate.plusDays(
            openRequiredDays
        )

        /*
         * Livramento condicional
         */
        val paroleRule = ParoleRuleProvider.getParoleRule(data)

        val paroleDate = DateCalculator.addFractionOfPenalty(
            baseDate = baseDate,
            penalty = data.penaltyDuration(),
            percentage = paroleRule.percentage
        )

        return CalculationResult(
            semiOpenDate = semiOpenDate.format(dateFormatter),
            openDate = openDate.format(dateFormatter),
            paroleDate = paroleDate.format(dateFormatter)
        )
    }
}