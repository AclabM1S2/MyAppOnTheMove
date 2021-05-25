package com.pdarcas.myapponthemove.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.pdarcas.myapponthemove.databinding.FragmentLoginBinding
import com.pdarcas.myapponthemove.ui.activities.MainActivity
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding by fragmentAutoCleared()
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailLogin = _binding.etEmailLogin
        val passwordLogin = _binding.etPasswordLogin

        _binding.btnLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(emailLogin.text.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(requireActivity(), "Please enter email.", Toast.LENGTH_SHORT)
                        .show()
                }

               !Patterns.EMAIL_ADDRESS.matcher(emailLogin.text.toString()).matches() -> {
                   Toast.makeText(requireActivity(), "Invalid email format", Toast.LENGTH_SHORT)
                       .show()
               }

                TextUtils.isEmpty(passwordLogin.text.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(requireActivity(), "Please enter password.", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    loginViewModel.signIn(
                        emailLogin.text.toString(),
                        passwordLogin.text.toString()
                    )
                        .observe(viewLifecycleOwner, Observer {
                            it?.let {
                                val intent = Intent(requireActivity(), MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                activity?.finish()
                            } //TODO  IF NOT FOUND DISPLAY TOAST
                        })
                }
            }
        }
    }
}