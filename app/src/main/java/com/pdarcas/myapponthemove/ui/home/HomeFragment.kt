package com.pdarcas.myapponthemove.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pdarcas.myapponthemove.R
import com.pdarcas.myapponthemove.databinding.FragmentHomeBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

const val REQUEST_KEY = "request"
class HomeFragment : Fragment()  {

    /*private lateinit var homeViewModel: HomeViewModel*/
    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding by fragmentAutoCleared()
    private var myPosition : GeoPoint? = null
    private var tracking = false
    lateinit var model: SharedViewModel
    /* Bouton pour ouvrir la modal*/
    private var btnShowDialog: FloatingActionButton? = null
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
                    line.addPoint(GeoPoint(50.633333, 3.066667))
                    line.addPoint(GeoPoint(50.833333, 3.866667))
                    line.addPoint(GeoPoint(it.latitude,it.longitude))
                    Log.d("GeoPoint me : ", GeoPoint(it.latitude,it.longitude).toString())
                    _binding.map.overlays.add(line);
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


        btnShowDialog = view.findViewById(R.id.fab)
        btnShowDialog?.setOnClickListener {
            findNavController().navigate(R.id.homeDialog)
        }


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

        _binding.buttonMyPosition.setOnClickListener{

            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            var fusedLocation = homeViewModel.location.fusedLocationClient
            fusedLocation.lastLocation.addOnSuccessListener { Location ->
                myPosition = GeoPoint(Location.latitude,Location.longitude)
                if(myPosition != null){
                    Marker(_binding.map).apply {
                        position = myPosition
                        _binding.map.overlays.add(this)
                    }
                }
                this.onViewCreated(view, bundleOf())
             }

        }

        if(positionUser){
            Log.d("Home","START")


        }
        _binding.buttonStart.setOnClickListener{
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            homeViewModel.location.observe(viewLifecycleOwner, Observer {

                val line = Polyline(_binding.map)
                line.addPoint(GeoPoint(50.633333, 3.066667))
                line.addPoint(GeoPoint(50.833333, 3.866667))
                line.addPoint(GeoPoint(it.latitude,it.longitude))
                _binding.map.overlays.add(line);


            })

            /*val roadManager: RoadManager = OSRMRoadManager(_binding.map.context)
            roadManager.addRequestOption("routeType=pedestrian")
                homeViewModel.onActive()
                Log.d("PositionGeoLoc 0",homeViewModel.onActive().equals(false).toString())

                        homeViewModel.startLocationUpdates()
                        var fusedLocation = homeViewModel.location.fusedLocationClient
                        fusedLocation.lastLocation.addOnSuccessListener { Location ->
                            var currentLocation = GeoPoint(Location.latitude,Location.longitude)
                            Log.d("PositionGeoLoc 1",currentLocation.toString())
                            waypoints.add(currentLocation)
                            val road = roadManager.getRoad(waypoints)
                            if (road.mStatus != Road.STATUS_OK){
                                Log.d("Road error bro : ",road.mStatus.toString())
                            }
                            *//*roadManager.addRequestOption("routeType=bicycle")*//*
                            val roadOverlay = RoadManager.buildRoadOverlay(road)
                            _binding.map.overlays.add(roadOverlay);
                            Log.d("PositionGeoLoc 2",currentLocation.toString() )
                            this.onViewCreated(view, bundleOf())
                        }*/

        }

        _binding.buttonStop.setOnClickListener{

        }

        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        model.positionUser.observe(viewLifecycleOwner, Observer {

            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

/*            var fusedLocation = homeViewModel.location.fusedLocationClient
            fusedLocation.lastLocation.addOnSuccessListener { Location ->
                myPosition = GeoPoint(Location.latitude,Location.longitude)
                if(myPosition != null){
                    Marker(_binding.map).apply {
                        position = myPosition
                        _binding.map.overlays.add(this)
                        positionUser=false
                    }
                }
                this.onViewCreated(view, bundleOf())

            }*/
        }
        )
        

        model.actionCharger.observe(viewLifecycleOwner, Observer {
            charger = it
            Log.d("Home","RECEIVED start for open folder")
        })
        model.actionNaviguer.observe(viewLifecycleOwner, Observer {
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