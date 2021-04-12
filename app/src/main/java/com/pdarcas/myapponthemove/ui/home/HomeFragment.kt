package com.pdarcas.myapponthemove.ui.home

import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pdarcas.myapponthemove.databinding.FragmentHomeBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker


class HomeFragment : Fragment()  {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding by fragmentAutoCleared()
    private val startPoint = GeoPoint(48.13, -1.63)

    val waypoints = ArrayList<GeoPoint>()
    val endPoint = GeoPoint(48.4, -1.9)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

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

        val roadManager: RoadManager = OSRMRoadManager(_binding.map.context)

        waypoints.add(startPoint)
        waypoints.add(endPoint)
        val road = roadManager.getRoad(waypoints)

        if (road.mStatus != Road.STATUS_OK) {
            Toast.makeText(this.context, "Error when loading road", Toast.LENGTH_LONG).show()
        }

        val roadOverlay = RoadManager.buildRoadOverlay(road)
        _binding.map.getOverlays().add(roadOverlay);
        _binding.map.invalidate();

    }




}