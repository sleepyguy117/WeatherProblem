package com.problem.weather.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.problem.weather.R
import com.problem.weather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

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

        checkPermissionsAndRefreshWeather()
    }

    private fun getGPSAndFetchWeather() {
        val locationClient = LocationServices.getFusedLocationProviderClient(this)

        locationClient.lastLocation.addOnSuccessListener {
            homeViewModel.fetchWeather()
            Toast.makeText(this@MainActivity, "lat = ${it.latitude}, lon = ${it.longitude}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermissionsAndRefreshWeather() {
        if (hasRequiredPermissions()) {
            getGPSAndFetchWeather()
        } else {
            EasyPermissions.requestPermissions(this@MainActivity, resources.getString(R.string.permission_rational),
                1, *getRequiredPermissions())
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
        Timber.v("permissions granted")
        getGPSAndFetchWeather()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Timber.v("permissions denied")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}