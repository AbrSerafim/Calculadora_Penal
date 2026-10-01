package br.com.calculadorapenal.calculation

import br.com.calculadorapenal.model.CalculationData
import br.com.calculadorapenal.model.CrimeType
import br.com.calculadorapenal.model.InmateStatus

object ParoleRuleProvider {

    fun getParoleRule(
        data: CalculationData
    ): ParoleRule {

        return when {
            data.crimeType == CrimeType.COMUM &&
                    data.inmateStatus == InmateStatus.PRIMARIO -> {
                ParoleRule(1.0 / 3.0)
            }

            data.crimeType == CrimeType.COMUM &&
                    data.inmateStatus == InmateStatus.REINCIDENTE -> {
                ParoleRule(1.0 / 2.0)
            }

            data.crimeType == CrimeType.HEDIONDO_EQUIPARADO -> {
                ParoleRule(2.0 / 3.0)
            }

            else -> {
                throw IllegalArgumentException(
                    "No parole rule found."
                )
            }
        }
    }
}