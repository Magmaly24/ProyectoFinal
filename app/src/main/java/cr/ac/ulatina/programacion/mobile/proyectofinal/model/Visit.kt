package cr.ac.ulatina.programacion.mobile.proyectofinal.model

import java.util.UUID

data class Visit(
    val id: String = UUID.randomUUID().toString(),
    val fecha: Long = System.currentTimeMillis(),
    val operador: String = "",
    val piscina: String = "",
    val cloroInicial: Double = 0.0,
    val phInicial: Double = 0.0,
    val alcalinidadInicial: Double = 0.0,
    val durezaCalcica: Double = 0.0,
    val acidoCianuro: Double = 0.0,
    val notas: String = ""
)
