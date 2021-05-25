package com.pdarcas.myapponthemove.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pdarcas.myapponthemove.R

class ModalBottomSheetFragmentMenu: BottomSheetDialogFragment() {
    private val model: SharedViewModel by sharedViewModel()
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

        btnPosition=view.findViewById(R.id.btn_position)
        btnCharger = view.findViewById(R.id.btn_charger)
        btnNaviguer=view.findViewById(R.id.btn_naviguer)
        btnPosition?.setOnClickListener { model.positionUser.value=true
            dismiss()}
        btnNaviguer?.setOnClickListener { model.actionNaviguer.value=true
            dismiss()}
        btnCharger?.setOnClickListener { model.actionCharger.value=true
            dismiss()}
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }



}