package com.perpus.banyumas.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.perpus.banyumas.viewmodel.LoginViewModel
import com.perpus.banyumas.R
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
        snackBar("${data?.message}","Success")
        viewModel.saveIsLoginStatus(true)
        viewModel.saveUsername(data?.data?.user?.email.toString())
        viewModel.saveId(data?.data?.user?.idanggota.toString())
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

    }

    private fun processError(msg: String?) {
        snackBar("$msg","Error")
    }

    private fun snackBar(msg: String, status: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(status)
        builder.setMessage(msg)

        builder.setPositiveButton("OK") { dialog, which ->

        }
        val dialog = builder.create()
        dialog.show()
    }
    override fun onStart() {
        super.onStart()
        viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
}