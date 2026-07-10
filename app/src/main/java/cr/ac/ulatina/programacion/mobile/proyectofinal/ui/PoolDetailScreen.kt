package cr.ac.ulatina.programacion.mobile.proyectofinal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Pool

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoolDetailScreen(pool: Pool, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
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
                .padding(16.dp)
        ) {
            Text(text = pool.name, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(text = pool.owner, color = Color.Gray, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(24.dp))
            
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

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Pool Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            
            DetailRow("Owner", pool.owner)
            DetailRow("Management Company", pool.managementCompany)
            DetailRow("Size (m²)", "%.2f".format(pool.size))
            DetailRow("Visits Per Week", "%.2f".format(pool.visitsPerWeek))
            DetailRow("Monthly Payment", "%,.2f".format(pool.monthlyPayment))
        }
    }
}
