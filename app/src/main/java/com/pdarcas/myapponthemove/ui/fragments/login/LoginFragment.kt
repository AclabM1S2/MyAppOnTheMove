package com.pdarcas.myapponthemove.ui.fragments.login

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
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
    ): View {
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
                        .observe(viewLifecycleOwner, {
                            it?.let {
                                val intent = Intent(requireActivity(), MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                activity?.finish()
                            } ?: run {
                                Toast.makeText(
                                    requireContext(),
                                    "Bad email or password",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.d(ContentValues.TAG, "Login failed")
                            }
                        })
                }
            }
        }

        _binding.tvForgetPassword.setOnClickListener {

            val emailInput = EditText(requireContext())
            emailInput.inputType = EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Reset password?")
            alertDialog.setMessage("Enter your email to receive a reset link.")
            alertDialog.setView(emailInput)

            alertDialog.setPositiveButton("yes") { _: DialogInterface, _: Int ->
                loginViewModel.resetPassword(emailInput.text.toString())
                    .let {
                        Toast.makeText(requireContext(), "Reset email sent", Toast.LENGTH_LONG)
                            .show()
                    }
            }

            alertDialog.setNegativeButton("no") { _: DialogInterface, _: Int ->
                //empty
            }

            alertDialog.show()

        }
    }
}