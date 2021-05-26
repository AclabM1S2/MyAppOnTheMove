package com.pdarcas.myapponthemove.ui.activities


import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pdarcas.myapponthemove.R
import com.pdarcas.myapponthemove.databinding.MainActivityBinding
import com.pdarcas.myapponthemove.ui.fragments.home.ModalBottomSheetFragmentMenu
import com.pdarcas.myapponthemove.utils.activityViewBinding




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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }


}


