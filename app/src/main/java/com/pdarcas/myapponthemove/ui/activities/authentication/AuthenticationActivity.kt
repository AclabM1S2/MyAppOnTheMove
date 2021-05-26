package com.pdarcas.myapponthemove.ui.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pdarcas.myapponthemove.databinding.AuthenticationActivityBinding
import com.pdarcas.myapponthemove.ui.activities.MainActivity
import com.pdarcas.myapponthemove.ui.fragments.login.LoginFragment
import com.pdarcas.myapponthemove.ui.fragments.register.RegisterFragment
import com.pdarcas.myapponthemove.utils.activityViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationActivity : AppCompatActivity() {

    private val _binding by activityViewBinding(AuthenticationActivityBinding::inflate)
    private val authenticationViewModel: AuthenticationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        _binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        _binding.viewPager.adapter =
            LoginAdapter(supportFragmentManager, this.lifecycle, _binding.tabLayout.tabCount)
        TabLayoutMediator(
            _binding.tabLayout,
            _binding.viewPager
        ) { tab: TabLayout.Tab, i: Int ->
            tab.text = (if (i == 0) "Login" else "Register")
        }.attach()
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
        private val lifecycle: Lifecycle,
        private val totalTabs: Int
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = totalTabs
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    LoginFragment()
                }
                1 -> {
                    RegisterFragment()
                }
                else -> LoginFragment()
            }
        }
    }
}