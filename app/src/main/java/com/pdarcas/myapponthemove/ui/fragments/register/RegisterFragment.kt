package com.pdarcas.myapponthemove.ui.fragments.register

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pdarcas.myapponthemove.databinding.FragmentRegisterBinding
import com.pdarcas.myapponthemove.ui.activities.MainActivity
import com.pdarcas.myapponthemove.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding by fragmentAutoCleared()
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailRegister = _binding.etEmailRegister
        val passwordRegister = _binding.etPasswordRegister
        val passwordConfirmRegister = _binding.etPasswordConfirmRegister

        _binding.btnRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(emailRegister.text.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(requireActivity(), "Please enter email.", Toast.LENGTH_SHORT)
                        .show()
                }

                !Patterns.EMAIL_ADDRESS.matcher(emailRegister.text.toString()).matches() -> {
                    Toast.makeText(requireActivity(), "Invalid email format", Toast.LENGTH_SHORT)
                        .show()
                }

                (passwordRegister.text.toString() != passwordConfirmRegister.text.toString()) -> {
                    Toast.makeText(requireActivity(), "Passwords don't match", Toast.LENGTH_SHORT)
                        .show()
                }

                TextUtils.isEmpty(passwordRegister.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(requireActivity(), "Please enter password.", Toast.LENGTH_SHORT)
                        .show()
                }

                passwordRegister.text.length < 6 -> {
                    Toast.makeText(
                        requireActivity(),
                        "PPassword too short, enter minimum 6 characters!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                else -> {
                    registerViewModel.signUp(
                        emailRegister.text.toString(),
                        passwordRegister.text.toString()
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
                                    "Registration failed",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.d(ContentValues.TAG, "Login failed")
                            }
                        })
                }
            }
        }
    }
}