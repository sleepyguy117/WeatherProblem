package com.problem.weather.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.problem.weather.R
import com.problem.weather.databinding.ActivityMainBinding
import com.problem.weather.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.time.ZonedDateTime
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val homeViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        setClickListeners()
        setObservers()

        binding.dayAndTime.text = Utils.zoneDateTimeToString(ZonedDateTime.now())

        checkPermissionsAndRefreshWeather()
    }

    private fun setObservers() {

        homeViewModel.weatherLiveData().observe(this@MainActivity) {

            with(binding) {
                layoutCurrentDay.isVisible = true
                highValue.text = String.format(resources.getString(R.string.temperature_format, it.hi.roundToInt()))
                lowValue.text = String.format(resources.getString(R.string.temperature_format, it.low.roundToInt()))
                description.text = it.description

                Glide.with(this@MainActivity)
                    .load(Utils.makeIconUrl(it.icon))
                    .into(icon)
            }
        }
    }

    private fun setClickListeners() {

        with(binding) {
            layoutCurrentDay.setOnClickListener {
                if (homeViewModel.lat != null && homeViewModel.lon != null) {
                    DetailedActivity.openDetails(
                        this@MainActivity,
                        homeViewModel.lat!!,
                        homeViewModel.lon!!
                    )
                }
            }
        }
    }


    private fun getGPSAndFetchWeather() {
        val locationClient = LocationServices.getFusedLocationProviderClient(this)

        locationClient.lastLocation.addOnSuccessListener {
            homeViewModel.lat = it.latitude
            homeViewModel.lon = it.longitude

            homeViewModel.fetchCurrentDay()
            Toast.makeText(
                this@MainActivity,
                "lat = ${it.latitude}, lon = ${it.longitude}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkPermissionsAndRefreshWeather() {
        if (hasRequiredPermissions()) {
            getGPSAndFetchWeather()
        } else {
            EasyPermissions.requestPermissions(
                this@MainActivity, resources.getString(R.string.permission_rational),
                1, *getRequiredPermissions()
            )
        }
    }

    private fun getRequiredPermissions(): Array<String> {
        return arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun hasRequiredPermissions() =
        EasyPermissions.hasPermissions(this, *getRequiredPermissions())

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("test", "permissions granted")
        getGPSAndFetchWeather()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d("test", "permissions denied")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}