package com.perpus.banyumas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.perpus.banyumas.viewmodel.HomeViewModel
import com.perpus.banyumas.viewmodel.LoginViewModel
import com.perpus.banyumas.R
import com.perpus.banyumas.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUsername()
        toProfile()
        scanQR()
        detailPinjam()
        koleksiBuku()
        searchBuku()
    }

    private fun searchBuku() {
        binding.cardPencarian.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun koleksiBuku() {
        binding.cardKoleksi.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_koleksiFragment)
        }
    }


    private fun scanQR() {
        binding.cardPinjam.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scannerFragment)
        }
    }

    private fun detailPinjam() {
        binding.cardDetail.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_peminjamanByIdFragment)
        }
    }


    private fun toProfile() {
        binding.toProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun setUsername() {
        viewModel.getDataStoreUsername().observe(viewLifecycleOwner){
            binding.tvHi.text = "Hi, $it"
        }
    }

}