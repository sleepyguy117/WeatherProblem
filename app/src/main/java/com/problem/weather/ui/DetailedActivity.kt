package com.problem.weather.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.problem.weather.R
import com.problem.weather.data.Lce
import com.problem.weather.databinding.ActivityDetailedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedActivity : AppCompatActivity() {

    private val detailedViewModel: DetailedViewModel by viewModels()

    private lateinit var binding: ActivityDetailedBinding
    private val weatherHoursAdapter = WeatherHoursAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!intent.hasExtra(LAT) || !intent.hasExtra(LON)) {
            Toast.makeText(this@DetailedActivity, "error", Toast.LENGTH_SHORT).show()
        }

        detailedViewModel.lat = intent.getDoubleExtra(LAT, 0.0)
        detailedViewModel.lon = intent.getDoubleExtra(LON, 0.0)

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupRecyclerView()
        setObservers()

        detailedViewModel.fetchHours()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupRecyclerView() {

        with(binding) {
            weatherHoursList.layoutManager = LinearLayoutManager(this@DetailedActivity)
            weatherHoursList.adapter = weatherHoursAdapter
            weatherHoursList.addItemDecoration(
                DividerItemDecoration(
                    this@DetailedActivity,
                    DividerItemDecoration.VERTICAL
                ).apply {
                    setDrawable(
                        AppCompatResources.getDrawable(
                            this@DetailedActivity,
                            R.drawable.divider
                        )!!
                    )
                })
        }
    }

    private fun setObservers() {
        detailedViewModel.weatherHoursLiveData().observe(this@DetailedActivity) {

            when (it) {
                is Lce.Loading -> {
                    binding.loading.isVisible = true
                }

                is Lce.Content -> {
                    binding.loading.isVisible = false
                    weatherHoursAdapter.replaceData(it.data)
                }
                is Lce.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(this@DetailedActivity, "there was an error", Toast.LENGTH_SHORT).show()
                }
            }


        }
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