package br.com.calculadorapenal.calculation

import br.com.calculadorapenal.model.CalculationData
import br.com.calculadorapenal.model.CrimeType
import br.com.calculadorapenal.model.InmateStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object RuleProvider {

    fun getProgressionRule(
        data: CalculationData
    ): ProgressionRule {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val start = LocalDate.parse(data.startDate, formatter)

        val newLaw = LocalDate.of(2024, 3, 24)
        if (start.isBefore(newLaw)) {
            return when (data.crimeType) {
                CrimeType.COMUM if data.inmateStatus == InmateStatus.PRIMARIO -> {
                    ProgressionRule(.16)
                }

                CrimeType.COMUM if data.inmateStatus == InmateStatus.REINCIDENTE -> {
                    ProgressionRule(.20)
                }

                CrimeType.HEDIONDO_EQUIPARADO if data.inmateStatus == InmateStatus.PRIMARIO -> {
                    ProgressionRule(.40)
                }

                CrimeType.HEDIONDO_EQUIPARADO if data.inmateStatus == InmateStatus.REINCIDENTE -> {
                    ProgressionRule(.60)
                }

                else -> {
                    throw IllegalArgumentException(
                        "No parole rule found."
                    )
                }
            }
        } else {
            return when (data.crimeType) {
                CrimeType.COMUM if data.inmateStatus == InmateStatus.PRIMARIO -> {
                    ProgressionRule(.16)
                }

                CrimeType.COMUM if data.inmateStatus == InmateStatus.REINCIDENTE -> {
                    ProgressionRule(.20)
                }

                CrimeType.HEDIONDO_EQUIPARADO if data.inmateStatus == InmateStatus.PRIMARIO -> {
                    ProgressionRule(.70)
                }

                CrimeType.HEDIONDO_EQUIPARADO if data.inmateStatus == InmateStatus.REINCIDENTE -> {
                    ProgressionRule(.80)
                }

                else -> {
                    throw IllegalArgumentException(
                        "No parole rule found."
                    )
                }
            }
        }
    }
}