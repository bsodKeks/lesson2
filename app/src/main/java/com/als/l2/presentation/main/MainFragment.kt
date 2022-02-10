package com.als.l2.presentation.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.als.l2.R
import com.als.l2.databinding.FragmentMainBinding
import com.als.l2.model.Weather
import com.als.l2.view.MainActivity
import com.als.l2.model.AppState
import com.als.l2.model.City
import com.als.l2.presentation.details.DetailsFragment
import com.als.l2.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

const val REQUEST_CODE = 14
private const val REFRESH_PERIOD = 60000L
private const val MINIMAL_DISTANCE = 100f
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            openDetailsFragment(weather)
        }
    })
    private var isDataSetRus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            onChangeTowns()
        }
        binding.mainFragmentFABLocation.setOnClickListener { checkPermission() }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })

        showListOfTowns()
    }

    private fun checkPermission() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showRationaleDialog()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun showRationaleDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_rationale_title))
                .setMessage(getString(R.string.dialog_rationale_meaasge))
                .setPositiveButton(getString(R.string.dialog_rationale_give_access)) { _, _ ->
                    requestPermission()
                }
                .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        checkPermissionsResult(requestCode, grantResults)
    }

    private fun checkPermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermissions = 0
                if ((grantResults.isNotEmpty())) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermissions++
                        }
                    }
                    if (grantResults.size == grantedPermissions) {
                        getLocation()
                    } else {
                        showDialog(
                            getString(R.string.dialog_title_no_gps),
                            getString(R.string.dialog_message_no_gps)
                        )
                    }
                } else {
                    showDialog(
                        getString(R.string.dialog_title_no_gps),
                        getString(R.string.dialog_message_no_gps)
                    )
                }
                return
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun getLocation() {
        activity?.let { context ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Получить менеджер геолокаций
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    provider?.let {
                        // Будем получать геоположение через каждые 60 секунд или каждые 100 метров
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MINIMAL_DISTANCE,
                            onLocationListener
                        )
                    }
                } else {
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location == null) {
                        showDialog(
                            getString(R.string.dialog_title_gps_turned_off),
                            getString(R.string.dialog_message_last_location_unknown)
                        )
                    } else {
                        getAddressAsync(context, location)
                        showDialog(
                            getString(R.string.dialog_title_gps_turned_off),
                            getString(R.string.dialog_message_last_known_location)
                        )
                    }
                }
            } else {
                showRationaleDialog()
            }
        }
    }

    private val onLocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            context?.let {
                getAddressAsync(it, location)
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun getAddressAsync(
        context: Context,
        location: Location
    ) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                binding.mainFragmentFAB.post {
                    showAddressDialog(addresses[0].getAddressLine(0), location)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                    openDetailsFragment(
                        Weather(
                            City(
                                address,
                                location.latitude,
                                location.longitude
                            )
                        )
                    )
                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun openDetailsFragment(weather: Weather) {
        val manager = activity?.supportFragmentManager
        if (manager != null) {
            val bundle = Bundle()
            bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
            manager.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance(bundle))
                .addToBackStack("")
                .commit()
        }
    }


    private fun onChangeTowns(){
        changeWeatherDataSet()
        val prefs = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = prefs?.edit()
        editor?.putBoolean(Constants.IS_WORLD_KEY, !isDataSetRus)
        editor?.apply()
    }

    private fun showListOfTowns(){
        activity?.let{
            isDataSetRus = !it.getPreferences(Context.MODE_PRIVATE).getBoolean(Constants.IS_WORLD_KEY, false)
            getTowns()
        }
    }

    private fun getTowns() {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceRus()

            // binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceWorld()
            // binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
    }

    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            // binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            // binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.mainFragmentRecyclerView.isVisible = true
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
                binding.mainFragmentRecyclerView.isVisible = false
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainFragmentFAB, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.reload)) {viewModel.getWeatherFromLocalSourceRus() }
                    .show()
            }
        }
    }


    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    companion object {
        fun newInstance() =
            MainFragment()
    }
}

interface OnItemViewClickListener {
    fun onItemViewClick(weather: Weather)
}
