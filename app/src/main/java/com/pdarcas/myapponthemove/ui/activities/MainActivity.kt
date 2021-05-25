package com.pdarcas.myapponthemove.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pdarcas.myapponthemove.utils.activityViewBinding


import com.pdarcas.myapponthemove.databinding.MainActivityBinding

import com.pdarcas.myapponthemove.ui.home.ModalBottomSheetFragmentMenu


class MainActivity : AppCompatActivity() {
    private val binding by activityViewBinding(MainActivityBinding::inflate)


    private lateinit var btnOpen: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnOpen = binding.fab
        btnOpen.setOnClickListener {

            val bottomSheetFragmentMenu: ModalBottomSheetFragmentMenu =
                ModalBottomSheetFragmentMenu.getInstance()
            bottomSheetFragmentMenu.show(supportFragmentManager, ModalBottomSheetFragmentMenu.TAG)
        }
    }

}


