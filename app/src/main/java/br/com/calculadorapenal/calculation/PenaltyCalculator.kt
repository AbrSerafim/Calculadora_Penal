package br.com.calculadorapenal.calculation

import br.com.calculadorapenal.model.CalculationData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

        val progressionRule = RuleProvider.getProgressionRule(
            data
        )

        val semiOpenDate = DateCalculator.addFractionOfPenalty(
            baseDate = baseDate,
            penalty = data.penaltyDuration(),
            percentage = progressionRule.percentage
        )

        val formattedSemiOpenDate =
            semiOpenDate.format(dateFormatter)

        return CalculationResult(
            semiOpenDate = formattedSemiOpenDate,
            openDate = formattedSemiOpenDate,
            paroleDate = null
        )
    }
}