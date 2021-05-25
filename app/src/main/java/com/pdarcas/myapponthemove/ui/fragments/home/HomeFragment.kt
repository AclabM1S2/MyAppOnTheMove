package com.pdarcas.myapponthemove.ui.fragments.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.pdarcas.myapponthemove.databinding.FragmentHomeBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline


class HomeFragment : Fragment()  {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private var _binding: FragmentHomeBinding by fragmentAutoCleared()
    private var myPosition : GeoPoint? = null

    /* Boolean pour lancer la geolocalisation */
    private var positionUser:Boolean = false
    /* Boolean pour lancer la navigation */
    private var naviguer:Boolean = false
    /* Boolean pour charger un gpx */
    private var charger:Boolean = false


    private val permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permission ->
        Log.e("PermissionCheck","Appel permission")
        if(!permission.values.contains((false))){
            if(positionUser) {
                homeViewModel.location.observe(viewLifecycleOwner, Observer {
                    Marker(_binding.map).apply {
                        position = GeoPoint(it.latitude, it.longitude)
                        _binding.map.overlays.add(this)
                        positionUser=false
                    }
                })
            } else if (naviguer) {
                homeViewModel.location.observe(viewLifecycleOwner, Observer {
                    val line = Polyline(_binding.map)
                    if(waypoints.isNotEmpty()){
                        line.addPoint(waypoints.last())
                    }
                    line.addPoint(GeoPoint(it.latitude,it.longitude))
                    waypoints.add(GeoPoint(it.latitude,it.longitude))
                    Log.d("GeoPoint me : ", GeoPoint(it.latitude,it.longitude).toString())
                    _binding.map.overlays.add(line);
                    _binding.buttonStop.visibility = View.VISIBLE

                })
            } else if (charger) {

            }

        }

    }

    val waypoints = ArrayList<GeoPoint>()
    val endPoint = GeoPoint(50.633333, 3.066667)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentHomeBinding.inflate(inflater, container, false)

        return _binding.root
    }




    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        Configuration.getInstance().userAgentValue = requireContext().packageName
        _binding.map.apply {
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
            setMultiTouchControls(true)

            val mapController = controller
            mapController.setZoom(3.0)
            if(myPosition != null){
                mapController.setCenter(myPosition)
            }
            this.isTilesScaledToDpi=true
        }

        _binding.map.invalidate();


        _binding.buttonStop.setOnClickListener{

        }

        /* Observables de la Modal*/

        homeViewModel.positionUser.observe(viewLifecycleOwner, Observer {
            positionUser=it
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

        }
        )


        homeViewModel.actionCharger.observe(viewLifecycleOwner, Observer {
            charger = it
            Log.d("Home","RECEIVED start for open folder")
        })

        homeViewModel.actionNaviguer.observe(viewLifecycleOwner, Observer {
            naviguer = it
            Log.d("Home","RECEIVED start for navigation")
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

        })

    }

}

