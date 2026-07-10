package cr.ac.ulatina.programacion.mobile.proyectofinal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Visit
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitDetailScreen(visit: Visit, onBack: () -> Unit) {
    val scrollState = rememberScrollState()
    val sdf = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
    val dateString = sdf.format(Date(visit.fecha))

    val targetCloro = 2.0
    val targetPhMin = 7.2
    val targetPhMax = 7.6
    val targetAlcalinidadMin = 80.0
    val targetAlcalinidadMax = 120.0
    val targetDurezaMin = 200.0
    val targetAcidoMin = 30.0

    val poolSize = 25.4 
    val cloroFaltante = (targetCloro - visit.cloroInicial) * poolSize

    val cloroInst = when {
        visit.cloroInicial > targetCloro -> "Cloro Alto. No aplicar producto, monitorear en la próxima visita."
        visit.cloroInicial < targetCloro -> "Cloro Bajo. Aplicar Cloro según sea necesario."
        else -> "Cloro en niveles óptimos."
    }

    val phInst = when {
        visit.phInicial < targetPhMin -> "Aplicar Incrementador de pH (pH+)"
        visit.phInicial > targetPhMax -> "Aplicar Reductor de pH (pH-)"
        else -> "pH en niveles óptimos."
    }

    val alcalinidadInst = when {
        visit.alcalinidadInicial < targetAlcalinidadMin -> "Alcalinidad Baja. Aplicar Bicarbonato de Sodio."
        visit.alcalinidadInicial > targetAlcalinidadMax -> "Alcalinidad Alta. Aplicar Reductor."
        else -> "Alcalinidad en niveles óptimos."
    }

    val durezaInst = when {
        visit.durezaCalcica < targetDurezaMin -> "Dureza Baja. Aplicar Cloruro de Calcio."
        else -> "Dureza en niveles óptimos."
    }

    val acidoInst = when {
        visit.acidoCianuro < targetAcidoMin -> "Estabilizador Bajo. Aplicar Ácido Cianúrico en polvo"
        else -> "Ácido Cianúrico en niveles óptimos."
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { Text("1") } },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text("Water Measurements", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            DetailRow("pH", "%.2f".format(visit.phInicial))
            DetailRow("Chlorine (ppm)", "%.2f".format(visit.cloroInicial))
            DetailRow("Alkalinity (ppm)", "%.2f".format(visit.alcalinidadInicial))
            DetailRow("Dureza Calcica", "%.2f".format(visit.durezaCalcica))

            Spacer(modifier = Modifier.height(24.dp))
            Text("Pool Maintenance Visit", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Informacion Visita", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            DetailRow("Pool", visit.piscina)
            DetailRow("Fecha", dateString)
            DetailRow("Operador", visit.operador)
            DetailRow("Gramos Cloro Faltante", "%.2f".format(cloroFaltante))

            Spacer(modifier = Modifier.height(16.dp))
            InstructionRow("Instrucciones Cloro", cloroInst)
            InstructionRow("Instrucciones PH", phInst)
            InstructionRow("Instrucciones Alcalinidad", alcalinidadInst)
            InstructionRow("Instrucciones Dureza Calcica", durezaInst)
            InstructionRow("Instrucciones Acido Cianuro", acidoInst)

            Spacer(modifier = Modifier.height(24.dp))
            Text("Detalles", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            DetailRow("Notes", visit.notas)

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Edit */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE)),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit", color = Color.Black)
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 16.dp)) {
                Checkbox(checked = false, onCheckedChange = {})
                Text("Visita Completada")
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.SemiBold)
    }
    HorizontalDivider(color = Color(0xFFF0F0F0))
}

@Composable
fun InstructionRow(label: String, instruction: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray, modifier = Modifier.weight(1f))
        Text(
            text = instruction,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1.5f),
            fontSize = 14.sp
        )
    }
    HorizontalDivider(color = Color(0xFFF0F0F0))
}
