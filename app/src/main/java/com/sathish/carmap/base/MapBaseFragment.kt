package com.sathish.carmap.base

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback

abstract class MapBaseFragment : BaseFragment(), OnMapReadyCallback {

    private var gMap: GoogleMap? = null
    private var map: MapView? = null


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }
        if (map != null) map!!.onSaveInstanceState(mapViewBundle)
    }


    override fun onStart() {
        super.onStart()
        map?.onStart()
    }

    override fun onResume() {
        super.onResume()
        map?.onResume()
    }

    override fun onStop() {
        super.onStop()
        map?.onStop()
    }

    override fun onPause() {
        map?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        map?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map?.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        gMap = googleMap
    }


    protected open fun setUpMap(savedInstanceState: Bundle?, map: MapView) {
        var mapViewBundle: Bundle? = null
        this.map = map
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }
        map.onCreate(mapViewBundle)
        map.getMapAsync(this)
    }

    protected open fun getMap(): GoogleMap? {
        return gMap
    }


    companion object {
        private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

}