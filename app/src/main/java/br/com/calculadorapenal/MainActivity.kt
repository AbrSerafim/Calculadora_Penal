package br.com.calculadorapenal

import br.com.calculadorapenal.model.CalculationData
import br.com.calculadorapenal.calculation.PenaltyCalculator
import br.com.calculadorapenal.model.CrimeType
import br.com.calculadorapenal.model.InmateStatus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.calculadorapenal.ui.theme.Calculadora_PenalTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import android.content.Intent
import android.net.Uri


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Calculadora_PenalTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStartClick = {
                    navController.navigate("calculation")
                }
            )
        }

        composable("calculation") {
            CalculationScreen(
                onCalculateClick = { calculationData ->

                    val result = PenaltyCalculator.calculate(
                        calculationData
                    )

                    navController.navigate(
                        "results/" +
                                "${Uri.encode(result.semiOpenDate)}/" +
                                "${Uri.encode(result.openDate)}/" +
                                "${Uri.encode(result.paroleDate ?: "")}"
                    )
                }
            )
        }

        composable(
            route = "results/{semiOpenDate}/{openDate}/{paroleDate}"
        ) { backStackEntry ->

            ResultsScreen(
                semiOpenDate =
                    backStackEntry.arguments?.getString("semiOpenDate") ?: "",

                openDate =
                    backStackEntry.arguments?.getString("openDate") ?: "",

                paroleDate =
                    backStackEntry.arguments?.getString("paroleDate")
            )
        }
    }
}

@Composable
fun HomeScreen(
    onStartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Calculadora Penal",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Estime prazos relacionados à execução penal.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onStartClick
        ) {
            Text(text = "Iniciar Cálculo")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationScreen(
    onCalculateClick: (CalculationData) -> Unit
) {

    var years by remember { mutableStateOf("") }
    var months by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var startDate by remember { mutableStateOf("") }
    var detractionDays by remember { mutableStateOf("") }

    var crimeType by remember { mutableStateOf<CrimeType?>(null) }
    var crimeMenuExpanded by remember { mutableStateOf(false) }

    var inmateStatus by remember { mutableStateOf<InmateStatus?>(null) }
    var inmateStatusMenuExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    var showValidationError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(42.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )

    {

        Text(
            text = "Cálculo da Pena",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pena Total",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Informe o tempo total da pena:\n               (Com números)"
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = years,
            onValueChange = { years = it },
            label = {
                Text("Anos")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = months,
            onValueChange = { months = it },
            label = {
                Text("Meses")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = days,
            onValueChange = { days = it },
            label = {
                Text("Dias")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Data de início da pena",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                showDatePicker = true
            }
        ) {
            Text(
                if (startDate.isEmpty()) {
                    "Selecionar data"
                } else {
                    startDate
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tempo de Detração",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Informe o tempo de detração em dias:"
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = detractionDays,
            onValueChange = { detractionDays = it },
            label = {
                Text("Dias de detração")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tipo de Crime",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = crimeMenuExpanded,
            onExpandedChange = {
                crimeMenuExpanded = !crimeMenuExpanded
            }
        ) {
            OutlinedTextField(
                value = when (crimeType) {
                    CrimeType.COMUM -> "Comum"
                    CrimeType.HEDIONDO_EQUIPARADO -> "Hediondo/Equiparado"
                    null -> ""
                },
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Tipo de crime")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = crimeMenuExpanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = crimeMenuExpanded,
                onDismissRequest = {
                    crimeMenuExpanded = false
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Comum")
                    },
                    onClick = {
                        crimeType = CrimeType.COMUM
                        crimeMenuExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Hediondo/Equiparado")
                    },
                    onClick = {
                        crimeType = CrimeType.HEDIONDO_EQUIPARADO
                        crimeMenuExpanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Status do Apenado",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = inmateStatusMenuExpanded,
            onExpandedChange = {
                inmateStatusMenuExpanded = !inmateStatusMenuExpanded
            }
        ) {
            OutlinedTextField(
                value = when (inmateStatus) {
                    InmateStatus.PRIMARIO -> "Primário"
                    InmateStatus.REINCIDENTE -> "Reincidente"
                    null -> ""
                },
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Status")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = inmateStatusMenuExpanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = inmateStatusMenuExpanded,
                onDismissRequest = {
                    inmateStatusMenuExpanded = false
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Primário")
                    },
                    onClick = {
                        inmateStatus = InmateStatus.PRIMARIO
                        inmateStatusMenuExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Reincidente")
                    },
                    onClick = {
                        inmateStatus = InmateStatus.REINCIDENTE
                        inmateStatusMenuExpanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (showValidationError) {
            Text(
                text = "Preencha todos os campos antes de continuar.",
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {
                val yearsValue = years.toIntOrNull()
                val monthsValue = months.toIntOrNull()
                val daysValue = days.toIntOrNull()
                val detractionValue = detractionDays.toIntOrNull()

                val valid =
                    yearsValue != null &&
                            monthsValue != null &&
                            daysValue != null &&
                            detractionValue != null &&
                            yearsValue >= 0 &&
                            monthsValue >= 0 &&
                            daysValue >= 0 &&
                            detractionValue >= 0 &&
                            startDate.isNotBlank() &&
                            crimeType != null &&
                            inmateStatus != null

                if (valid) {
                    val calculationData = CalculationData(
                        years = yearsValue,
                        months = monthsValue,
                        days = daysValue,
                        startDate = startDate,
                        detractionDays = detractionValue,
                        crimeType = crimeType!!,
                        inmateStatus = inmateStatus!!
                    )

                    onCalculateClick(calculationData)
                } else {
                    showValidationError = true
                }
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            )

                            startDate = formatter.format(Date(millis))
                        }

                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

fun formatProcessNumber(input: String): String {
    val digits = input.filter { it.isDigit() }.take(20)

    return buildString {
        digits.forEachIndexed { index, char ->
            when (index) {
                7 -> append("-")
                9 -> append(".")
                13 -> append(".")
                14 -> append(".")
                16 -> append(".")
            }

            append(char)
        }
    }
}

@Composable
fun ResultsScreen(
    semiOpenDate: String,
    openDate: String,
    paroleDate: String?
) {

    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var whatsapp by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var processNumber by remember { mutableStateOf("") }

    var showValidationError by remember { mutableStateOf(false) }

    val scrollStateRes = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollStateRes)
            .padding(42.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Results

        Text(
            text = "Resultados",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Progressão ao semiaberto:")
        Text(semiOpenDate)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Progressão ao aberto:")
        Text(openDate)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Livramento condicional:")
        Text(
            if (paroleDate.isNullOrBlank()) {
                "Não disponível"
            } else {
                paroleDate
            }
        )

        // Contact
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Salvar ou enviar resultado",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Preencha seus dados para salvar ou enviar o resultado."
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Nome
        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                showValidationError = false
            },
            label = {
                Text("Nome Completo *")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // WhatsApp
        OutlinedTextField(
            value = whatsapp,
            onValueChange = {
                whatsapp = it
                showValidationError = false
            },
            label = {
                Text("Número de WhatsApp *")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // E-mail
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("E-mail")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Número do processo
        OutlinedTextField(
            value = processNumber,
            onValueChange = {
                processNumber = formatProcessNumber(it)
            },
            label = {
                Text("Número do Processo")
            },
            placeholder = {
                Text("NNNNNNN-DD.AAAA.J.TR.OOOO")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (showValidationError) {
            Text(
                text = "Preencha o nome completo e o WhatsApp.",
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        // =====================================================
        // SHARE RESULT
        // =====================================================

        Button(
            onClick = {

                if (
                    fullName.isBlank() ||
                    whatsapp.isBlank()
                ) {
                    showValidationError = true
                    return@Button
                }

                val resultText = """
                    Calculadora Penal
                    
                    Nome: $fullName
                    WhatsApp: $whatsapp
                    E-mail: ${email.ifBlank { "Não informado" }}
                    Número do Processo: ${processNumber.ifBlank { "Não informado" }}
                    
                    Progressão ao semiaberto: $semiOpenDate
                    Progressão ao aberto: $openDate
                    Livramento condicional: ${
                    paroleDate?.takeIf { it.isNotBlank() }
                        ?: "Não disponível"
                }
                
                    ==== CESPEDES LOURENÇO ADVOGADOS ====
                """.trimIndent()

                val sendIntent = Intent(
                    Intent.ACTION_SEND
                ).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        resultText
                    )
                }

                val shareIntent = Intent.createChooser(
                    sendIntent,
                    "Enviar resultado"
                )

                context.startActivity(shareIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar / Enviar Resultado")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =====================================================
        // LAWYER CTA
        // =====================================================

        Button(
            onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://cespedeslourencoadvogados.com.br/contato/"
                    )
                )

                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quero Falar com um Advogado")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}