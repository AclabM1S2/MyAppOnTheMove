package com.pdarcas.myapponthemove.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.pdarcas.myapponthemove.R

class ModalBottomSheetFragmentMenu: BottomSheetDialogFragment() {
    companion object{
        @JvmField val TAG = "ModalBottomSheetFragmentMenu"
        @JvmStatic fun getInstance():ModalBottomSheetFragmentMenu{
            return ModalBottomSheetFragmentMenu()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.modal_fragment_menu,container,false)
        val btn: MaterialButton =view.findViewById(R.id.btn_close)
        btn.setOnClickListener {
            dismiss()
        }
        return view
    }
}