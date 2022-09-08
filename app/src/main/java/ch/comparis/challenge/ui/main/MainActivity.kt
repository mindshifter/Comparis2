package ch.comparis.challenge.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.comparis.challenge.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        title = "Car Market"
    }
}