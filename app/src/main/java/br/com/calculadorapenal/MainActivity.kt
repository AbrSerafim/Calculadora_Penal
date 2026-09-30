package br.com.calculadorapenal

import br.com.calculadorapenal.model.CalculationData

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

                    navController.navigate(
                        "results/" +
                                "${calculationData.years}/" +
                                "${calculationData.months}/" +
                                "${calculationData.days}/" +
                                "${Uri.encode(calculationData.startDate)}/" +
                                "${calculationData.detractionDays}/" +
                                "${Uri.encode(calculationData.crimeType)}/" +
                                "${Uri.encode(calculationData.inmateStatus)}"
                    )
                }
            )
        }

        composable(
            route = "results/{years}/{months}/{days}/{startDate}/{detractionDays}/{crimeType}/{inmateStatus}"
        ) { backStackEntry ->

            ResultsScreen(
                years = backStackEntry.arguments?.getString("years") ?: "",
                months = backStackEntry.arguments?.getString("months") ?: "",
                days = backStackEntry.arguments?.getString("days") ?: "",
                startDate = backStackEntry.arguments?.getString("startDate") ?: "",
                detractionDays = backStackEntry.arguments?.getString("detractionDays") ?: "",
                crimeType = backStackEntry.arguments?.getString("crimeType") ?: "",
                inmateStatus = backStackEntry.arguments?.getString("inmateStatus") ?: ""
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

    var crimeType by remember { mutableStateOf("") }
    var crimeMenuExpanded by remember { mutableStateOf(false) }

    var inmateStatus by remember { mutableStateOf("") }
    var inmateStatusMenuExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

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
            text = "Informe o tempo total da pena:"
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
                value = crimeType,
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
                        crimeType = "Comum"
                        crimeMenuExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Hediondo_Equiparado")
                    },
                    onClick = {
                        crimeType = "Hediondo_Equiparado"
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
                value = inmateStatus,
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
                        inmateStatus = "Primário"
                        inmateStatusMenuExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Reincidente")
                    },
                    onClick = {
                        inmateStatus = "Reincidente"
                        inmateStatusMenuExpanded = false
                    }
                )
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val calculationData = CalculationData(
                    years = years.toIntOrNull() ?: 0,
                    months = months.toIntOrNull() ?: 0,
                    days = days.toIntOrNull() ?: 0,
                    startDate = startDate,
                    detractionDays = detractionDays.toIntOrNull() ?: 0,
                    crimeType = crimeType,
                    inmateStatus = inmateStatus
                )

                onCalculateClick(calculationData)
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

@Composable
fun ResultsScreen(
    years: String,
    months: String,
    days: String,
    startDate: String,
    detractionDays: String,
    crimeType: String,
    inmateStatus: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(42.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Resultados",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Pena: $years anos, $months meses e $days dias")

        Text("Início: $startDate")

        Text("Detração: $detractionDays dias")

        Text("Crime: $crimeType")

        Text("Status: $inmateStatus")
    }
}