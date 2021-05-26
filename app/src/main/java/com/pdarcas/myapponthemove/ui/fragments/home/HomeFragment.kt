package com.pdarcas.myapponthemove.ui.fragments.home

import android.Manifest
import android.R.attr.data
import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
//import com.google.firebase.firestore.FirebaseFirestore
import com.pdarcas.myapponthemove.data.entities.RecordModel
import com.pdarcas.myapponthemove.databinding.FragmentHomeBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private var _binding: FragmentHomeBinding by fragmentAutoCleared()
    private var myPosition: GeoPoint? = null

    /* Boolean pour lancer la geolocalisation */
    private var positionUser: Boolean = false

    /* Boolean pour lancer la navigation */
    private var naviguer: Boolean = false

    /* Boolean pour charger un gpx */
    private var charger: Boolean = false

    /* POur la creation du RecordModel*/
    private lateinit var currentDate: String;
    private val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    private val permissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            Log.e("PermissionCheck", "Appel permission")
            if (!permission.values.contains((false))) {
                if (positionUser) {
                    homeViewModel.location.observe(viewLifecycleOwner, Observer {
                        Marker(_binding.map).apply {
                            position = GeoPoint(it.latitude, it.longitude)
                            _binding.map.overlays.add(this)
                            positionUser = false
                        }
                    })
                } else if (naviguer) {
                    currentDate = sdf.format(Date())
                    homeViewModel.location.observe(viewLifecycleOwner, Observer {
                        val line = Polyline(_binding.map)
                        if (waypoints.isNotEmpty()) {
                            line.addPoint(waypoints.last())
                        }
                        line.addPoint(GeoPoint(it.latitude, it.longitude))
                        waypoints.add(GeoPoint(it.latitude, it.longitude))
                        Log.d("GeoPoint me : ", GeoPoint(it.latitude, it.longitude).toString())
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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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
            if (myPosition != null) {
                mapController.setCenter(myPosition)
            }
            this.isTilesScaledToDpi = true
        }

        _binding.map.invalidate();


        _binding.buttonStop.setOnClickListener {
            val record = RecordModel(
                UUID.randomUUID().toString(),
                currentDate,
                null,
                homeViewModel.getCurrentUser()?.email
            )
            record.setGeoPoints(waypoints)
            Log.e("Points", record.toString())
            //val db = FirebaseFirestore.getInstance()
            //db.collection("records").add(record)
            _binding.buttonStop.visibility = View.GONE

        }

        /* Observables de la Modal*/

        homeViewModel.positionUser.observe(viewLifecycleOwner, Observer {
            positionUser = it
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
            Log.d("Home", "RECEIVED start for open folder")
        })

        homeViewModel.actionNaviguer.observe(viewLifecycleOwner, Observer {
            naviguer = it
            Log.d("Home", "RECEIVED start for navigation")
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

        })

    }

}

