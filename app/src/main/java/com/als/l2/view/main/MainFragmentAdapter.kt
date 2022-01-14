package com.als.l2.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.als.l2.R
import com.als.l2.model.Weather

class MainFragmentAdapter(private var onItemViewClickListener: OnItemViewClickListener?):  RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_cities, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(weather: Weather) {
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text = weather.city.city
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    weather.city.city,
                    Toast.LENGTH_LONG
                ).show()
            }
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(weather)
            }

        }
    }

    fun removeListener() {
        onItemViewClickListener = null
    }
}

