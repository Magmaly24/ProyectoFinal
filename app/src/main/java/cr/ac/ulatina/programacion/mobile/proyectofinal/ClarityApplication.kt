package cr.ac.ulatina.programacion.mobile.proyectofinal

import android.app.Application

import cr.ac.ulatina.programacion.mobile.proyectofinal.seed.DataSeeder

class ClarityApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataSeeder.seedDatabase(this)
    }
}
