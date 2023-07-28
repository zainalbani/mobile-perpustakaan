package com.perpus.banyumas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.perpus.banyumas.data.response.AuthResponse
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    processLogin(it.data)
                }
                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val mail = binding.email.text.toString()
            val pwd = binding.password.text.toString()
            viewModel.loginUser(email = mail, pwd = pwd)
        }
    }

    private fun processLogin(data: AuthResponse?) {
        showToast("Success:" + data?.msg)
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun processError(msg: String?) {
        showToast("Error:$msg")
    }

    private fun showToast(msg: String) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}