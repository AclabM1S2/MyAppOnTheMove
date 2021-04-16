package com.pdarcas.myapponthemove.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.pdarcas.myapponthemove.R

class LoginTabFragment : Fragment() {
    var email: EditText? = null
    var pass: EditText? = null
    var forgetPass: TextView? = null
    var login: Button? = null
    var v = 0f
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.login_fragment, container, false) as ViewGroup
        email = root.findViewById(R.id.email)
        pass = root.findViewById(R.id.password)
        forgetPass = root.findViewById(R.id.forget_password)
        login = root.findViewById(R.id.buttonLogin)


        return root
    }
}
