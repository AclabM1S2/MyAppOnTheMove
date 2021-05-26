package com.pdarcas.myapponthemove.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pdarcas.myapponthemove.R
import kotlinx.android.synthetic.main.fragment_bottomsheet.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false)
    }

     fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)

         navigationView.setNavigationItemSelectedListener {
            dismiss()
            when (it.itemId) {
                R.id.nav1 -> context?.toast("${it.title} clicked")
                R.id.nav2 -> context?.toast("${it.title} clicked")
                R.id.nav3 -> context?.toast("${it.title} clicked")
            }
            true
        }
    }
}