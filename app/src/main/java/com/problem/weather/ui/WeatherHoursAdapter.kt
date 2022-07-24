package com.problem.weather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.problem.weather.R
import com.problem.weather.data.WeatherHours
import com.problem.weather.databinding.ItemHourWeatherBinding
import com.problem.weather.utils.Utils
import kotlin.math.roundToInt

class WeatherHoursAdapter: RecyclerView.Adapter<WeatherHoursAdapter.WeatherHourViewHolder>() {

    private val weatherHoursList = mutableListOf<WeatherHours>()

    class WeatherHourViewHolder(private val binding: ItemHourWeatherBinding): RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(data: WeatherHours) {
            with(binding) {
                tempValue.text = String.format(context.getString(R.string.temperature_format, data.temp.roundToInt()))
                description.text = data.description
                timeValue.text = Utils.timestampToHour(data.timestamp)

                Glide.with(context)
                    .load(Utils.makeIconUrl(data.icon))
                    .into(icon)

                scrim.isVisible = Utils.isTimeStampInPast(data.timestamp)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHourViewHolder {
        val binding = ItemHourWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherHourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherHourViewHolder, position: Int) {
        holder.bind(weatherHoursList[position])
    }

    override fun getItemCount(): Int {
        return weatherHoursList.size
    }

    fun replaceData(newWeatherHours: List<WeatherHours>) {
        weatherHoursList.clear()
        weatherHoursList.addAll(newWeatherHours)
        notifyDataSetChanged()
    }
}