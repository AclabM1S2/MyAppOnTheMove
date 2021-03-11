package com.pdarcas.myapponthemove.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pdarcas.myapponthemove.databinding.FragmentDashboardBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding:FragmentDashboardBinding by fragmentAutoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding= FragmentDashboardBinding.inflate(inflater,container,false)

        return _binding.root
    }
}