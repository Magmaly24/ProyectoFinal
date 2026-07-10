package cr.ac.ulatina.programacion.mobile.proyectofinal.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClarityDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "clarity.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        createPoolsTable(db)
        createVisitsTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS visits")
        db.execSQL("DROP TABLE IF EXISTS pools")
        onCreate(db)
    }

    private fun createPoolsTable(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE pools (
                id TEXT PRIMARY KEY,
                name TEXT NOT NULL,
                owner TEXT NOT NULL,
                managementCompany TEXT,
                size REAL,
                visitsPerWeek REAL,
                monthlyPayment REAL
            )
        """.trimIndent())
    }

    private fun createVisitsTable(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE visits (
                id TEXT PRIMARY KEY,
                fecha INTEGER NOT NULL,
                operador TEXT NOT NULL,
                piscina TEXT NOT NULL,
                cloroInicial REAL,
                phInicial REAL,
                alcalinidadInicial REAL,
                durezaCalcica REAL,
                acidoCianuro REAL,
                notas TEXT
            )
        """.trimIndent())
    }
}
