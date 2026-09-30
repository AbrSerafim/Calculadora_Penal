package br.com.calculadorapenal.model

data class CalculationData(
    val years: Int,
    val months: Int,
    val days: Int,
    val startDate: String,
    val detractionDays: Int,
    val crimeType: String,
    val inmateStatus: String
)