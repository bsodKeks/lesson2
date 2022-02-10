package com.als.l2

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class GeoZones : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        val geofencingClient: GeofencingClient =
            LocationServices.getGeofencingClient(this)

        val geofence: Geofence = Geofence.Builder()
            .setRequestId("fgd")
            .setCircularRegion(55.755826, 37.617299900000035, 100)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(listOf(geofence)).build()

        geofencingClient.addGeofences(geofencingRequest, intent)




    }
}