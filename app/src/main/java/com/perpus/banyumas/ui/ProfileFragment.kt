package com.perpus.banyumas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpus.banyumas.R
import com.perpus.banyumas.adapter.SearchBookAdapter
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.databinding.FragmentProfileBinding
import com.perpus.banyumas.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        logout()
        ubahPassword()
        viewModel.getId().observe(viewLifecycleOwner) {
            viewModel.getUserProfile(it)
        }

        viewModel.userResult.observe(viewLifecycleOwner) {
            when(it){
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvNama.text = it.data!!.data.nama
                    binding.tvKategori.text = it.data.data.kategori
                    binding.tvPhone.text = it.data.data.nohp
                    binding.tvId.text = it.data.data.idanggota

                }

                is BaseResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${it.msg}", Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
        }
    }

    private fun ubahPassword() {
        binding.btnEdit.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_editPasswordFragment)
        }
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener{
            viewModel.removeIsLoginStatus()
            viewModel.removeId()
            viewModel.removeUsername()
            viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }
}