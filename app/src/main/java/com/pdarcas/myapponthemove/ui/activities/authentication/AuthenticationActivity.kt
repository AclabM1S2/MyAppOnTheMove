package com.pdarcas.myapponthemove.ui.activities.authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.pdarcas.myapponthemove.ui.activities.MainActivity
import com.pdarcas.myapponthemove.utils.activityViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationActivity : AppCompatActivity() {

    private val _binding by activityViewBinding(AuthenticationActivityBinding::inflate)
    private val authenticationViewModel: AuthenticationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide();
        _binding.tabLayout.addTab(_binding.tabLayout.newTab().setText("Login"))
        _binding.tabLayout.addTab(_binding.tabLayout.newTab().setText("SignUp"))
        _binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        _binding.viewPager.adapter =
            LoginAdapter(supportFragmentManager, this, _binding.tabLayout.tabCount)
        _binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(_binding.tabLayout))

        _binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                _binding.viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


    override fun onStart() {
        super.onStart()

        authenticationViewModel.getCurrentFirebaseUser()
            ?.let {
                val intent = Intent(this@AuthenticationActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
    }

    internal class LoginAdapter(
        fragmentManager: FragmentManager,
        var context: Context,
        var totalTabs: Int
    ) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    LoginFragment()
                }
                1 -> {
                    RegisterFragment()
                }
                else -> getItem(position)
            }
        }

        override fun getCount(): Int = totalTabs

    }
}