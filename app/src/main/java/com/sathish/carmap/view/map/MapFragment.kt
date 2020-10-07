package com.sathish.carmap.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.sathish.carmap.R
import com.sathish.carmap.base.BaseMapMarkerFragment
import com.sathish.carmap.databinding.FragmentCarMapBinding
import com.sathish.carmap.view.car.CarListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.gms.common.api.ApiException as ApiException1


class MapFragment : BaseMapMarkerFragment() {


    private lateinit var googleMap: GoogleMap

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false


    private lateinit var binding: FragmentCarMapBinding

    private val carListViewModel: CarListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarMapBinding.inflate(inflater, container, false)
        getLocationPermission()
        setObserver()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onClick()
    }

    private fun onClick() {
        binding.btnSwitchToList.setOnClickListener {
            it.findNavController().navigate(R.id.action_mapFragment_to_carListFragment)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMap(savedInstanceState, binding.map)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.setGroupVisible(R.id.group_car, false)
    }


    @SuppressLint("MissingPermission")
    override fun onMapSetReady(map: GoogleMap?) {
        googleMap = map ?: return
        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()
        carListViewModel.getMarkerData()
    }


    private fun setObserver() {

        carListViewModel.getMarkerLiveData()?.observe(viewLifecycleOwner, this::addMarker)


        carListViewModel.errorMessage.observe(viewLifecycleOwner, {
            when (it) {
                getString(R.string.str_network_error) -> showMessage(it)
                else -> showMessage(it)
            }

        })
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true

        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                println("OnRequest Permission")
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                    updateLocationUI()
                }
            }
        }

    }

    private fun updateLocationUI() {
        if (googleMap == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                googleMap.isMyLocationEnabled = false
                googleMap.uiSettings?.isMyLocationButtonEnabled = false
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = (10 * 1000).toLong()
        locationRequest.fastestInterval = 2000

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        val locationSettingsRequest = builder.build()

        val result =
            LocationServices.getSettingsClient(requireActivity())
                .checkLocationSettings(locationSettingsRequest)
        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException1::class.java)
                if (response!!.locationSettingsStates.isLocationPresent) {
                    getCurrentLocation()
                }
            } catch (exception: ApiException1) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvable = exception as ResolvableApiException
                        resolvable.startResolutionForResult(
                            requireActivity(),
                            REQUEST_CHECK_SETTINGS
                        )
                    } catch (e: IntentSender.SendIntentException) {
                    } catch (e: ClassCastException) {
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        }


    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->

            if (task.isSuccessful && task.result != null) {


            } else {
                showMessage(getString(R.string.str_error_location))
            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check for the integer request code originally supplied to startResolutionForResult().
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            when (resultCode) {
                Activity.RESULT_OK -> showMessage("I'm present")
                Activity.RESULT_CANCELED -> {
                }
            }
        }
    }

    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        const val REQUEST_CHECK_SETTINGS = 43
    }


}