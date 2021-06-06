package com.pdarcas.myapponthemove.ui.fragments.home

import android.Manifest
import android.Manifest.permission.FOREGROUND_SERVICE
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import com.pdarcas.myapponthemove.data.entities.ListPts
import com.pdarcas.myapponthemove.data.entities.RecordModel
import com.pdarcas.myapponthemove.data.entities.WayPoints
import com.pdarcas.myapponthemove.data.entities.pts
import com.pdarcas.myapponthemove.databinding.FragmentHomeBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import okhttp3.internal.userAgent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
    /* POur la creation du RecordModel*/
    private lateinit var currentDate:String;
    private val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    private val permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permission ->
        Log.e("PermissionCheck", "Appel permission")
        if(!permission.values.contains((false))){
            if(positionUser) {
                homeViewModel.location.observe(viewLifecycleOwner, {
                    Marker(_binding.map).apply {
                        position = GeoPoint(it.latitude, it.longitude)
                        _binding.map.controller.setZoom(16.0)
                        _binding.map.controller.setCenter(position)
                        _binding.map.overlays.add(this)
                        positionUser = false
                    }
                })
                homeViewModel.location.removeObservers(viewLifecycleOwner)
            } else if (naviguer) {
                currentDate = sdf.format(Date())
                homeViewModel.location.observe(viewLifecycleOwner, {
                    val line = Polyline(_binding.map)
                    if (waypoints.isNotEmpty()) {
                        line.addPoint(waypoints.last())
                    }
                    line.addPoint(GeoPoint(it.latitude, it.longitude))
                    waypoints.add(GeoPoint(it.latitude, it.longitude))
                    Log.d("GeoPoint me : ", GeoPoint(it.latitude, it.longitude).toString())
                    _binding.map.controller.setZoom(16.0)
                    _binding.map.controller.setCenter(waypoints.last())
                    _binding.map.overlays.add(line);
                    _binding.buttonStop.visibility = View.VISIBLE

                })
            } else if (charger) {
                val db = FirebaseFirestore.getInstance()
                var line = Polyline(_binding.map)
                //var points = ArrayList<GeoPoint>()
                db.collection("records").whereEqualTo("id", "f31cb582-d402-4140-a4e7-10d0989949e8")
                    .get().addOnSuccessListener { result ->
                        var tmp = result.documents[0]
                        /*var theRecord=
                            RecordModel(
                                tmp.get("id") as String,
                                tmp.get("name") as String,
                                tmp.data?.get("points") as ArrayList<GeoPoint>,
                                tmp.get("idUser") as String
                            )*/
                        /*Log.e("ddd'",result.documents[0].toObject(RecordModel::class.java)!!.toString())*/

                        var tmp2 = result.documents[0]["points"]
                        Log.d("ddd",tmp2.)
                        /*Log.d("posi",tmp2.)
                        var position = GeoPoint(tmp2[0].latitude, tmp2[0].longitude)
                        _binding.map.controller.setZoom(16.0)
                        _binding.map.controller.setCenter(position)*/
/*                        for (pt in tmp2){
                            line.addPoint(GeoPoint(pt.latitude, pt.longitude))
                        }*/
                        _binding.map.overlays.add(line)
                        _binding.map.invalidate()

                    }



            }

        }

    }

    var waypoints = ArrayList<GeoPoint>()
    val endPoint = GeoPoint(50.633333, 3.066667)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentHomeBinding.inflate(inflater, container, false)

        return _binding.root
    }




    @RequiresApi(Build.VERSION_CODES.P)
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
            val record = RecordModel(
                UUID.randomUUID().toString(),
                currentDate,
                waypoints,
                homeViewModel.getCurrentUser()?.email
            )

            homeViewModel.location.removeObservers(viewLifecycleOwner)

            val db = FirebaseFirestore.getInstance()
            db.collection("records").add(record)
            _binding.buttonStop.visibility=View.GONE

        }

        /* Observables de la Modal*/

        homeViewModel.positionUser.observe(viewLifecycleOwner, {
            positionUser = it
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            )

        }
        )


        homeViewModel.actionCharger.observe(viewLifecycleOwner, Observer {
            charger = it
            Log.d("Home", "RECEIVED start for open folder")
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    FOREGROUND_SERVICE
                )
            )


        })

        homeViewModel.actionNaviguer.observe(viewLifecycleOwner, Observer {
            naviguer = it
            Log.d("Home", "RECEIVED start for navigation")
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    FOREGROUND_SERVICE
                )
            )

        })

    }

}

