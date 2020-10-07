package com.sathish.carmap.base

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Location
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sathish.carmap.BuildConfig
import com.sathish.carmap.data.model.CarResponseApiItem


abstract class BaseMapMarkerFragment : MapBaseFragment() {

    private val addedMarker: HashSet<Marker> = HashSet()
    private var userMarker: Marker? = null


    protected open fun addMarker(markerModelList: List<CarResponseApiItem>?) {
        getMap()?.clear()
        if (markerModelList != null) {
            if (markerModelList.isNotEmpty()) zoom(
                markerModelList[0].location.latitude,
                markerModelList[0].location.longitude
            )
        }


        if (BuildConfig.DEBUG && markerModelList == null) {
            error("Assertion failed")
        }
        if (markerModelList != null) {
            for (markerModel in markerModelList) {
                addCustomMarker(markerModel)
            }
        }
    }


    private fun zoom(latitude: Double, longitude: Double) {
        getMap()!!.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latitude, longitude),
                15.0f
            )
        )
    }

     internal fun updateUserLocation(userLocation: Location) {
        userMarker?.remove()
        val markerOptions = MarkerOptions()
        val ny = LatLng(userLocation.altitude, userLocation.longitude)
        markerOptions.position(ny)
        val marker = getMap()!!.addMarker(markerOptions)
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        userMarker = marker
    }


    private fun addCustomMarker(markerModel: CarResponseApiItem) {

        Glide.with(this)
            .asBitmap().circleCrop()
            .load(markerModel.model.photoUrl)
            .into(object : CustomTarget<Bitmap>(150, 150) {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    val markerOptions = MarkerOptions()
                    val latLng = LatLng(
                        markerModel.location.latitude,
                        markerModel.location.longitude
                    )
                    markerOptions.position(latLng)
                    markerOptions.title(markerModel.location.address)
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resource))
                    val marker: Marker? = getMap()?.addMarker(markerOptions)
                    marker?.tag = markerModel
                    addedMarker.add(marker!!)
                }
            })

    }
}