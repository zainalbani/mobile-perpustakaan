package com.perpus.banyumas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpus.banyumas.adapter.BukuAdapter
import com.perpus.banyumas.data.response.DataAllBook
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
        setAdapter()
        scanQR()
        detailPinjam()
    }

    private fun scanQR() {
        binding.btnQR.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scannerFragment)
        }
    }

    private fun detailPinjam() {
        binding.btnDetail.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_peminjamanByIdFragment)
        }
    }

    private fun setAdapter() {
        val adapter = BukuAdapter()
        homeViewModel.getAllBook()
        homeViewModel.buku.observe(viewLifecycleOwner){
            if (it != null){
                adapter.setData(it.data )
            }
        }
        binding.rvBuku.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvBuku.adapter = adapter
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