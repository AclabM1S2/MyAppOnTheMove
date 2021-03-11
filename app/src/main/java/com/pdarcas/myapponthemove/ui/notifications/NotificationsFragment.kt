package com.pdarcas.myapponthemove.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pdarcas.myapponthemove.databinding.FragmentNotificationsBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding by fragmentAutoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding= FragmentNotificationsBinding.inflate(inflater,container,false)

        return _binding.root
    }
}