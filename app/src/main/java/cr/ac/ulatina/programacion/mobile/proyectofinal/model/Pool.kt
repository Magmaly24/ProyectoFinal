package cr.ac.ulatina.programacion.mobile.proyectofinal.model

import java.util.UUID

data class Pool(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val owner: String = "",
    val managementCompany: String = "",
    val size: Double = 0.0,
    val visitsPerWeek: Double = 0.0,
    val monthlyPayment: Double = 0.0
)
