package br.com.calculadorapenal.model

import br.com.calculadorapenal.calculation.PenaltyDuration

data class CalculationData(
    val years: Int,
    val months: Int,
    val days: Int,
    val startDate: String,
    val detractionDays: Int,
    val crimeType: String,
    val inmateStatus: String
) {
    fun penaltyDuration(): PenaltyDuration {
        return PenaltyDuration(
            years = years,
            months = months,
            days = days
        )
    }
}