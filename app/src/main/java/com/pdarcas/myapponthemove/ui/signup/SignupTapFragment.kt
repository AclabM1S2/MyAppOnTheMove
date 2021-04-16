package com.pdarcas.myapponthemove.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pdarcas.myapponthemove.R

class SignupTapFragment : Fragment() {
    private lateinit var root : ViewGroup
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.signup_tab_fragment, container, false) as ViewGroup
        return root
    }
}