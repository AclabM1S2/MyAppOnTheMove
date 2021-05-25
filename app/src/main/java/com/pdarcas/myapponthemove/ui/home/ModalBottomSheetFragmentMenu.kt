package com.pdarcas.myapponthemove.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.pdarcas.myapponthemove.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ModalBottomSheetFragmentMenu: BottomSheetDialogFragment() {

    private val model: HomeViewModel by sharedViewModel()
    private  lateinit var btnNaviguer: Button
    private lateinit var btnPosition: Button
    private lateinit var btnCharger: Button
    companion object{
        @JvmField val TAG = "ModalBottomSheetFragmentMenu"
        @JvmStatic fun getInstance():ModalBottomSheetFragmentMenu{
            return ModalBottomSheetFragmentMenu()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.modal_fragment_menu,container,false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPosition=view.findViewById(R.id.btn_position)
        btnCharger = view.findViewById(R.id.btn_charger)
        btnNaviguer=view.findViewById(R.id.btn_naviguer)
        btnPosition?.setOnClickListener { model.positionUser.value=true
            dismiss()}
        btnNaviguer?.setOnClickListener { model.actionNaviguer.value=true
            dismiss()}
        btnCharger?.setOnClickListener { model.actionCharger.value=true
            dismiss()}

    }



}