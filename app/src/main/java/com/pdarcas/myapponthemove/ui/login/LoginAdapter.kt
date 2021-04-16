package com.pdarcas.myapponthemove.ui.login

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pdarcas.myapponthemove.ui.signup.SignupTapFragment

internal class LoginAdapter (fm: FragmentManager,
var context: Context,
var totalTabs: Int): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LoginTabFragment()
            }
            1 -> {
                SignupTapFragment()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int = totalTabs


}





