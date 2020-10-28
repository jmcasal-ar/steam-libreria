package com.example.steam.preferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.steam.R

class PreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        //Crear y mostrar nuestro fragmento
        showPreferencesFragment()
    }

    private fun showPreferencesFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containter, PreferenceFragment())
            .commit()
        //reemplazamos lo que contiene el id que creamos en el xml con la instancia de nuestro fragmento
    }
}