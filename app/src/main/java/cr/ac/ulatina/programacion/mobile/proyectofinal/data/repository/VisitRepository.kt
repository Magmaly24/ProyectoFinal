package cr.ac.ulatina.programacion.mobile.proyectofinal.data.repository

import android.content.ContentValues
import android.content.Context
import cr.ac.ulatina.programacion.mobile.proyectofinal.data.db.ClarityDatabaseHelper
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Pool
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Visit

class VisitRepository(context: Context) {

    private val dbHelper = ClarityDatabaseHelper(context)

    fun getAllVisits(): List<Visit> {
        val visits = mutableListOf<Visit>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM visits ORDER BY fecha DESC", null)

        if (cursor.moveToFirst()) {
            do {
                visits.add(Visit(
                    id = cursor.getString(0),
                    fecha = cursor.getLong(1),
                    operador = cursor.getString(2),
                    piscina = cursor.getString(3),
                    cloroInicial = cursor.getDouble(4),
                    phInicial = cursor.getDouble(5),
                    alcalinidadInicial = cursor.getDouble(6),
                    durezaCalcica = cursor.getDouble(7),
                    acidoCianuro = cursor.getDouble(8),
                    notas = cursor.getString(9)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return visits
    }

    fun getAllPools(): List<Pool> {
        val pools = mutableListOf<Pool>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM pools ORDER BY name ASC", null)

        if (cursor.moveToFirst()) {
            do {
                pools.add(Pool(
                    id = cursor.getString(0),
                    name = cursor.getString(1),
                    owner = cursor.getString(2),
                    managementCompany = cursor.getString(3),
                    size = cursor.getDouble(4),
                    visitsPerWeek = cursor.getDouble(5),
                    monthlyPayment = cursor.getDouble(6)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return pools
    }

    fun insertVisit(visit: Visit): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", visit.id)
            put("fecha", visit.fecha)
            put("operador", visit.operador)
            put("piscina", visit.piscina)
            put("cloroInicial", visit.cloroInicial)
            put("phInicial", visit.phInicial)
            put("alcalinidadInicial", visit.alcalinidadInicial)
            put("durezaCalcica", visit.durezaCalcica)
            put("acidoCianuro", visit.acidoCianuro)
            put("notas", visit.notas)
        }
        return db.insert("visits", null, values)
    }

    fun insertPool(pool: Pool): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", pool.id)
            put("name", pool.name)
            put("owner", pool.owner)
            put("managementCompany", pool.managementCompany)
            put("size", pool.size)
            put("visitsPerWeek", pool.visitsPerWeek)
            put("monthlyPayment", pool.monthlyPayment)
        }
        return db.insert("pools", null, values)
    }
}
