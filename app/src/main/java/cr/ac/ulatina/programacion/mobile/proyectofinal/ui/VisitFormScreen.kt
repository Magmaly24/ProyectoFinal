package cr.ac.ulatina.programacion.mobile.proyectofinal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Visit
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitFormScreen(viewModel: VisitViewModel) {
    val pools by viewModel.allPools.collectAsState()
    
    var operador by remember { mutableStateOf("Adriana Franco") }
    var selectedPool by remember { mutableStateOf("") }
    var cloro by remember { mutableStateOf("") }
    var ph by remember { mutableStateOf("") }
    var alcalinidad by remember { mutableStateOf("") }
    var dureza by remember { mutableStateOf("") }
    var acido by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
    val currentDateTime = sdf.format(Date())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Visitas", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        
        FormField(label = "Fecha", value = currentDateTime, onValueChange = {}, isReadOnly = true)
        FormField(label = "Operador", value = operador, onValueChange = { operador = it })
        
        Text(text = "Piscina", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedPool,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                placeholder = { Text("Seleccione una piscina") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                pools.forEach { pool ->
                    DropdownMenuItem(
                        text = { Text(pool.name) },
                        onClick = {
                            selectedPool = pool.name
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        FormField(label = "Cloro Inicial", value = cloro, onValueChange = { cloro = it }, keyboardType = KeyboardType.Number)
        FormField(label = "PH Inicial", value = ph, onValueChange = { ph = it }, keyboardType = KeyboardType.Number)
        FormField(label = "Alcalinidad Inicial", value = alcalinidad, onValueChange = { alcalinidad = it }, keyboardType = KeyboardType.Number)
        FormField(label = "Dureza Calcica", value = dureza, onValueChange = { dureza = it }, keyboardType = KeyboardType.Number)
        FormField(label = "Acido Cianuro", value = acido, onValueChange = { acido = it }, keyboardType = KeyboardType.Number)
        FormField(label = "Notas", value = notas, onValueChange = { notas = it })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val visit = Visit(
                    cloroInicial = cloro.toDoubleOrNull() ?: 0.0,
                    phInicial = ph.toDoubleOrNull() ?: 0.0,
                    alcalinidadInicial = alcalinidad.toDoubleOrNull() ?: 0.0,
                    durezaCalcica = dureza.toDoubleOrNull() ?: 0.0,
                    acidoCianuro = acido.toDoubleOrNull() ?: 0.0,
                    notas = notas,
                    operador = operador,
                    piscina = selectedPool
                )
                viewModel.insertVisit(visit)
                cloro = ""; ph = ""; alcalinidad = ""; dureza = ""; acido = ""; notas = ""
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit", color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isReadOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = label, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            readOnly = isReadOnly,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}
