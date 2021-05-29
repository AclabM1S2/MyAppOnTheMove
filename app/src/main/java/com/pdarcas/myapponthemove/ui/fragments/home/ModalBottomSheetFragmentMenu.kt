package com.pdarcas.myapponthemove.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pdarcas.myapponthemove.R
import com.pdarcas.myapponthemove.databinding.FragmentBottomModalBinding
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ModalBottomSheetFragmentMenu : BottomSheetDialogFragment() {

    private val model: HomeViewModel by sharedViewModel()
    private var _binding: FragmentBottomModalBinding by fragmentAutoCleared()
    private lateinit var btnNaviguer: Button
    private lateinit var btnPosition: Button
    private lateinit var btnCharger: Button

    companion object {
        @JvmField
        val TAG = "ModalBottomSheetFragmentMenu"
        @JvmStatic
        fun getInstance(): ModalBottomSheetFragmentMenu {
            return ModalBottomSheetFragmentMenu()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBottomModalBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPosition = _binding.btnPosition
        btnCharger = _binding.btnCharger
        btnNaviguer = _binding.btnNaviguer
        btnPosition?.setOnClickListener {
            model.positionUser.value = true
            dismiss()
        }
        btnNaviguer?.setOnClickListener {
            model.actionNaviguer.value = true
            dismiss()
        }
        btnCharger?.setOnClickListener {
            model.actionCharger.value = true
            dismiss()
        }
    }


}