package cr.ac.ulatina.programacion.mobile.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Pool
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Visit
import cr.ac.ulatina.programacion.mobile.proyectofinal.ui.*
import cr.ac.ulatina.programacion.mobile.proyectofinal.ui.theme.ProyectoFinalTheme

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object VisitForm : Screen("visit_form", "Visits Form", Icons.Default.ChatBubbleOutline)
    object Pools : Screen("pools", "Pools", Icons.Default.WaterDrop)
    object Visits : Screen("visits", "Visits", Icons.Default.Checklist)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinalTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel: VisitViewModel = viewModel()
    val screens = listOf(Screen.VisitForm, Screen.Pools, Screen.Visits)
    
    var selectedVisit by remember { mutableStateOf<Visit?>(null) }
    var selectedPool by remember { mutableStateOf<Pool?>(null) }

    Scaffold(
        topBar = {
            if (selectedVisit == null && selectedPool == null) {
                CenterAlignedTopAppBar(
                    title = { Text("CLARITY SOLUTIONS", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                    navigationIcon = {
                        IconButton(onClick = { /* Handle menu */ }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black
                    )
                )
            }
        },
        bottomBar = {
            if (selectedVisit == null && selectedPool == null) {
                NavigationBar(containerColor = Color.White) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    screens.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(screen.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Black,
                                selectedTextColor = Color.Black,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.Transparent
                            ),
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        when {
            selectedVisit != null -> {
                VisitDetailScreen(visit = selectedVisit!!, onBack = { selectedVisit = null })
            }
            selectedPool != null -> {
                PoolDetailScreen(pool = selectedPool!!, onBack = { selectedPool = null })
            }
            else -> {
                NavHost(
                    navController = navController,
                    startDestination = Screen.VisitForm.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.VisitForm.route) { VisitFormScreen(viewModel) }
                    composable(Screen.Pools.route) { 
                        PoolsScreen(viewModel, onPoolClick = { selectedPool = it }) 
                    }
                    composable(Screen.Visits.route) { 
                        VisitsListScreen(viewModel, onVisitClick = { selectedVisit = it }) 
                    }
                }
            }
        }
    }
}
