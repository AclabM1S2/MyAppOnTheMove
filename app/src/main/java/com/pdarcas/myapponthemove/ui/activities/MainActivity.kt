package com.pdarcas.myapponthemove.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.math.MathUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pdarcas.myapponthemove.R
import com.pdarcas.myapponthemove.databinding.MainActivityBinding
import com.pdarcas.myapponthemove.ui.activities.authentication.AuthenticationActivity
import com.pdarcas.myapponthemove.ui.fragments.home.ModalBottomSheetFragmentMenu
import com.pdarcas.myapponthemove.utils.activityViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val _binding by activityViewBinding(MainActivityBinding::inflate)
    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding.fab.setOnClickListener {

            val bottomSheetFragmentMenu: ModalBottomSheetFragmentMenu =
                ModalBottomSheetFragmentMenu.getInstance()
            bottomSheetFragmentMenu.show(supportFragmentManager, ModalBottomSheetFragmentMenu.TAG)
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(_binding.navView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        _binding.bottomAppBar.setNavigationOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        _binding.navView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            when (menuItem.itemId) {
                R.id.logout -> mainActivityViewModel.signOut()
                    .let {
                        val intent = Intent(this@MainActivity, AuthenticationActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
            }
            menuItem.isChecked = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            true
        }

    }
}