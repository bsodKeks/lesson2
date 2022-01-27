package com.als.l2.presentation.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.als.l2.R
import com.als.l2.databinding.FragmentDetailsBinding
import com.als.l2.model.Weather
import com.als.l2.utils.showSnackBar
import com.als.l2.model.AppState
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this)[DetailsViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        getWeather()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setWeather(weather: Weather) {
        val city = weatherBundle.city
        binding.cityName.text = city.city
        binding.cityCoordinates.text = "${getString(R.string.city_coordinates)} ${city.lat} ${city.lon}"
        binding.temperatureValue.text = weather.temperature.toString()
        binding.feelsLikeValue.text = weather.feelsLike.toString()
        binding.weatherCondition.text = weather.condition

        Glide.with(requireContext())
            .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
            .into(binding.ivPicture)

//        weather.icon?.let {
//            GlideToVectorYou.justLoadImage(
//                activity,
//                Uri.parse("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg"),
//                weatherIcon
//            )
//        }


    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { getWeather() })
            }


        }
    }

    private fun onError(){
        Snackbar
            .make(binding.temperatureLabel, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.reload)) {getWeather() }
            .show()
    }

    private fun getWeather() {
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat,weatherBundle.city.lon)

    }

    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}