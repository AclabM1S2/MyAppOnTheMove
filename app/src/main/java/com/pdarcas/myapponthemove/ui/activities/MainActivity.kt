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

        _binding.scrim.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val baseColor = Color.BLACK
                // 60% opacity
                val baseAlpha = ResourcesCompat.getFloat(resources, R.dimen.material_emphasis_medium)
                //Map slideOffset from [-1.0, 1.0] to [0.0, 1.0]
                val offset = (slideOffset - (-1f)) / (1f - (-1f)) * (1f - 0f) + 0f
                val alpha = com.google.android.material.math.MathUtils.lerp(0f, 255f, offset * baseAlpha).toInt()
                val color = Color.argb(alpha, baseColor.red, baseColor.green, baseColor.blue)
                _binding.scrim.setBackgroundColor(color)
            }
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // empty -> overridden method
            }
        })
    }
}