package com.pdarcas.myapponthemove.ui.home

import android.Manifest
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pdarcas.myapponthemove.databinding.FragmentHomeBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.Marker


class HomeFragment : Fragment()  {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding by fragmentAutoCleared()
    private val startPoint = GeoPoint(48.13, -1.63)

    private val permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permission ->
        if(!permission.values.contains((false))){


        }

    }

    private val getDocumentResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        uri?.let{
            Log.d("MyURI",uri.path!!)
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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)




        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance().userAgentValue = requireContext().packageName
        _binding.map.apply {
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
            setMultiTouchControls(true)

            val mapController = controller
            mapController.setZoom(3.0)
            mapController.setCenter(startPoint)
            this.isTilesScaledToDpi=true

        }

        Marker(_binding.map).apply {


            position = startPoint
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = "Start point"
            //startMarker.setIcon(getResources().getDrawable(R.drawable.marker_kml_point).mutate());
            //startMarker.setImage(getResources().getDrawable(R.drawable.ic_launcher));
            //startMarker.setInfoWindow(new MarkerInfoWindow(R.layout.bonuspack_bubble_black, map));
            //startMarker.setIcon(getResources().getDrawable(R.drawable.marker_kml_point).mutate());
            //startMarker.setImage(getResources().getDrawable(R.drawable.ic_launcher));
            //startMarker.setInfoWindow(new MarkerInfoWindow(R.layout.bonuspack_bubble_black, map));
            isDraggable = true
            setOnMarkerDragListener(object : Marker.OnMarkerDragListener {
                override fun onMarkerDrag(marker: Marker?) {
                    TODO("Not yet implemented")
                }

                override fun onMarkerDragEnd(marker: Marker?) {
                    TODO("Not yet implemented")
                }

                override fun onMarkerDragStart(marker: Marker?) {
                    TODO("Not yet implemented")
                }
            })
            _binding.map.getOverlays().add(this)

        }

/*        //val roadManager: RoadManager = OSRMRoadManager(_binding.map.context)
        val roadManager: RoadManager = MapQuestRoadManager("NJGmHg2Jmj7RFaE4pqME24qAMYjSdjV0")

        waypoints.add(startPoint)
        waypoints.add(endPoint)
        roadManager.addRequestOption("routeType=bicycle");
        val road = roadManager.getRoad(waypoints)


        if (road.mStatus != Road.STATUS_OK) {
            Toast.makeText(this.context, "Error when loading road", Toast.LENGTH_LONG).show()
        }

        val roadOverlay = RoadManager.buildRoadOverlay(road)
        _binding.map.getOverlays().add(roadOverlay);
        _binding.map.invalidate();*/

        val kmlDocument = KmlDocument()
        val url = "http://mapsengine.google.com/map/kml?forcekml=1&mid=z6IJfj90QEd4.kUUY9FoHFRdE"
        kmlDocument.parseKMLUrl(url)

        val kmlOverlay = kmlDocument.mKmlRoot.buildOverlay(_binding.map, null, null, kmlDocument) as FolderOverlay

        _binding.map.getOverlays().add(kmlOverlay);
        _binding.map.invalidate();

        _binding.buttonMyPosition.setOnClickListener{
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            homeViewModel.location.observe(viewLifecycleOwner, Observer{
                homeViewModel.onActive()
                var myPosition = homeViewModel.startLocationUpdates().toString()
                Log.d("myPosition", myPosition);
            })
        }

    }






}