package com.perpus.banyumas.ui

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.perpus.banyumas.viewmodel.ProfileViewModel
import com.perpus.banyumas.R
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.data.response.PasswordResponse
import com.perpus.banyumas.databinding.FragmentEditPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPasswordFragment : DialogFragment() {
    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentEditPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentEditPasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateResult.observe(viewLifecycleOwner) {
            when (it) {

                is BaseResponse.Success -> {
                    processUpdate(it.data)
                }
                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                }
            }
        }

        binding.btnUpdate.setOnClickListener {
            val oldPassword = binding.etOldPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            val confirmNewPassword = binding.etConfNewPassword.text.toString()

            viewModel.getId().observe(viewLifecycleOwner) { data ->
                viewModel.updatePassword(oldPassword, newPassword, confirmNewPassword, data)
            }
        }

    }

    private fun processError(msg: String?) {
        snackBar("$msg", "Error")

    }

    private fun processUpdate(data: PasswordResponse?) {
        snackBar("${data?.message}", "Success")
        findNavController().navigate(R.id.action_editPasswordFragment_to_profileFragment)
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
}