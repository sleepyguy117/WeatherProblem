package com.problem.weather.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.problem.weather.databinding.ActivityDetailedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedActivity : AppCompatActivity() {

    private val detailedViewModel : DetailedViewModel by viewModels()

    private lateinit var binding: ActivityDetailedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!intent.hasExtra(LAT) || !intent.hasExtra(LON)) {
            Toast.makeText(this@DetailedActivity, "error", Toast.LENGTH_SHORT).show()
        }

        val lat = intent.getDoubleExtra(LAT, 0.0)
        val lon = intent.getDoubleExtra(LON, 0.0)

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    companion object {

        private const val LAT = "LAT"
        private const val LON = "LON"

        fun openDetails(context: Context, lat: Double, lon: Double) {
            val intent = Intent(context, DetailedActivity::class.java).apply {
                putExtra(LAT, lat)
                putExtra(LON, lon)
            }

            context.startActivity(intent)
        }
    }
}