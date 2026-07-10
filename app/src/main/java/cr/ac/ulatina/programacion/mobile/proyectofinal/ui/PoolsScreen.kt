package cr.ac.ulatina.programacion.mobile.proyectofinal.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Pool

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoolsScreen(viewModel: VisitViewModel, onPoolClick: (Pool) -> Unit) {
    val pools by viewModel.allPools.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Pool")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Pools", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF0F0F0),
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                Text(text = "POOL NAME", modifier = Modifier.weight(1f), fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                Text(text = "OWNER", modifier = Modifier.weight(1f), fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            }
            
            HorizontalDivider()

            LazyColumn {
                items(pools) { pool ->
                    PoolItem(pool, onPoolClick)
                    HorizontalDivider()
                }
            }
        }
    }

    if (showDialog) {
        AddPoolDialog(
            onDismiss = { showDialog = false },
            onConfirm = { name, owner, company, size, visits, payment ->
                viewModel.insertPool(
                    Pool(
                        name = name,
                        owner = owner,
                        managementCompany = company,
                        size = size.toDoubleOrNull() ?: 0.0,
                        visitsPerWeek = visits.toDoubleOrNull() ?: 0.0,
                        monthlyPayment = payment.toDoubleOrNull() ?: 0.0
                    )
                )
                showDialog = false
            }
        )
    }
}

@Composable
fun PoolItem(pool: Pool, onClick: (Pool) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onClick(pool) }
    ) {
        Text(text = pool.name, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        Text(text = pool.owner, modifier = Modifier.weight(1f))
    }
}

@Composable
fun AddPoolDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var visits by remember { mutableStateOf("") }
    var payment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Pool") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Pool Name") })
                TextField(value = owner, onValueChange = { owner = it }, label = { Text("Owner") })
                TextField(value = company, onValueChange = { company = it }, label = { Text("Management Company") })
                TextField(value = size, onValueChange = { size = it }, label = { Text("Size (m²)") })
                TextField(value = visits, onValueChange = { visits = it }, label = { Text("Visits Per Week") })
                TextField(value = payment, onValueChange = { payment = it }, label = { Text("Monthly Payment") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, owner, company, size, visits, payment) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
