package cr.ac.ulatina.programacion.mobile.proyectofinal.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Visit
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VisitsListScreen(viewModel: VisitViewModel, onVisitClick: (Visit) -> Unit) {
    val visits by viewModel.allVisits.collectAsState()
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Historial de Visitas",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (visits.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No hay visitas registradas")
            }
        } else {
            LazyColumn {
                items(visits) { visit ->
                    VisitItem(visit, sdf.format(Date(visit.fecha)), onVisitClick)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun VisitItem(visit: Visit, dateString: String, onClick: (Visit) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(visit) }
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = visit.piscina, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = dateString, color = Color.Gray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Operador: ${visit.operador}", color = Color.DarkGray)
        Text(text = "Cloro: ${visit.cloroInicial} ppm | pH: ${visit.phInicial}", color = Color.Gray, fontSize = 12.sp)
    }
}
