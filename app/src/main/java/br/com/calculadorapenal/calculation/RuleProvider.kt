package br.com.calculadorapenal.calculation

import br.com.calculadorapenal.model.CalculationData
import br.com.calculadorapenal.model.CrimeType
import br.com.calculadorapenal.model.WithViolence
import br.com.calculadorapenal.model.InmateStatus

object RuleProvider {

    fun getProgressionRule(
        data: CalculationData
    ): ProgressionRule {

        return when {
                    data.crimeType == CrimeType.COMUM &&
                            data.violenceType == WithViolence.SEM &&
                            data.inmateStatus == InmateStatus.PRIMARIO -> {
                ProgressionRule(0.16)
            }
            data.crimeType == CrimeType.COMUM &&
                    data.violenceType == WithViolence.COM &&
                    data.inmateStatus == InmateStatus.PRIMARIO -> {
                ProgressionRule(0.25)
            }

            data.crimeType == CrimeType.COMUM &&
                    data.violenceType == WithViolence.SEM &&
                    data.inmateStatus == InmateStatus.REINCIDENTE -> {
                ProgressionRule(0.20)
            }

            data.crimeType == CrimeType.COMUM &&
                    data.violenceType == WithViolence.COM &&
                    data.inmateStatus == InmateStatus.REINCIDENTE -> {
                ProgressionRule(0.30)
            }

            data.crimeType == CrimeType.HEDIONDO_EQUIPARADO &&
                    data.violenceType == WithViolence.SEM &&
                    data.inmateStatus == InmateStatus.PRIMARIO -> {
                ProgressionRule(0.40)
            }

            data.crimeType == CrimeType.HEDIONDO_EQUIPARADO &&
                    data.violenceType == WithViolence.COM &&
                    data.inmateStatus == InmateStatus.PRIMARIO -> {
                ProgressionRule(0.50)
            }

            data.crimeType == CrimeType.HEDIONDO_EQUIPARADO &&
                    data.violenceType == WithViolence.SEM &&
                    data.inmateStatus == InmateStatus.REINCIDENTE -> {
                ProgressionRule(0.60)
            }

            data.crimeType == CrimeType.HEDIONDO_EQUIPARADO &&
                    data.violenceType == WithViolence.COM &&
                    data.inmateStatus == InmateStatus.REINCIDENTE -> {
                ProgressionRule(0.70)
            }

            else -> {
                throw IllegalArgumentException("No progression rule found.")
            }
        }
    }
}