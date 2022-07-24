package com.problem.weather.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.problem.weather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel : MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)


        Glide.with(this@MainActivity)
            .load("https://openweathermap.org/img/wn/10d@2x.png")
            .into(binding.image)


        binding.detailed.setOnClickListener {
            startActivity(Intent(this@MainActivity, DetailedActivity::class.java))
        }

        homeViewModel.fetchWeather()


    }
}