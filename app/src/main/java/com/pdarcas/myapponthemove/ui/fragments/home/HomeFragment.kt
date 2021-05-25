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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pdarcas.myapponthemove.R
import com.pdarcas.myapponthemove.databinding.FragmentHomeBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker

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
            Log.d("PermissionCheck","Dans le IF")
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

            homeViewModel.onActive()
            homeViewModel.startLocationUpdates()
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
            homeViewModel.onInactive()
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

            val roadManager: RoadManager = OSRMRoadManager(_binding.map.context)
   /*         roadManager.addRequestOption("routeType=pedestrian")
*/
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
                            /*roadManager.addRequestOption("routeType=bicycle")*/
                            val roadOverlay = RoadManager.buildRoadOverlay(road)
                            _binding.map.overlays.add(roadOverlay);
                            Log.d("PositionGeoLoc 2",currentLocation.toString() )
                            this.onViewCreated(view, bundleOf())
                        }

        }

        _binding.buttonStop.setOnClickListener{
            homeViewModel.onInactive()
        }

        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        model.positionUser.observe(viewLifecycleOwner, Observer {

            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            homeViewModel.onActive()
            homeViewModel.startLocationUpdates()
            var fusedLocation = homeViewModel.location.fusedLocationClient
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

            }
            homeViewModel.onInactive()


        }
        )
        

        model.actionCharger.observe(viewLifecycleOwner, Observer {
            charger = it
            Log.d("Home","RECEIVED start for open folder")
        })
        model.actionNaviguer.observe(viewLifecycleOwner, Observer {
            naviguer = it
            Log.d("Home","RECEIVED start for navigation")
        })

    }

}