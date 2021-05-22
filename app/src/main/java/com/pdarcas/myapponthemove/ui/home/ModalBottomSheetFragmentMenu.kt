package com.pdarcas.myapponthemove.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
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

        return inflater.inflate(R.layout.modal_fragment_menu,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnPosition: MaterialButton =view.findViewById(R.id.btn_position)
        btnPosition.setOnClickListener {
            val result = true
            // Use the Kotlin extension in the fragment-ktx artifact
            Log.d("Avant setFragmentResult","Avant setFragmentResult")
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
            Log.d("Apres setFragmentResult","Apres setFragmentResult")
            dismiss()
        }
    }
}