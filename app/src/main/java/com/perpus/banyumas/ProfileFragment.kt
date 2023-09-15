package com.perpus.banyumas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.perpus.banyumas.databinding.FragmentProfileBinding
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

        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    tvNama.text = it.data?.nama.toString()
                    tvKategori.text = it.data?.kategori.toString()
                    tvPhone.text = it.data?.nohp.toString()
                    tvId.text = it.data?.idanggota.toString()

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