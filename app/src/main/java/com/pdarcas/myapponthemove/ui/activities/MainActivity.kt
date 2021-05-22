package com.pdarcas.myapponthemove.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mvince.m1test.utils.activityViewBinding
import com.pdarcas.myapponthemove.R

import com.pdarcas.myapponthemove.databinding.MainActivityBinding

import com.pdarcas.myapponthemove.ui.home.ModalBottomSheetFragmentMenu

class MainActivity : AppCompatActivity() {
    private val binding by activityViewBinding(MainActivityBinding::inflate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}


