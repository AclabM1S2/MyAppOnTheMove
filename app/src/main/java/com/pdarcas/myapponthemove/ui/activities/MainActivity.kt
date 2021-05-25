package com.pdarcas.myapponthemove.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.pdarcas.myapponthemove.utils.activityViewBinding

import com.pdarcas.myapponthemove.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private val binding by activityViewBinding(MainActivityBinding::inflate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}


