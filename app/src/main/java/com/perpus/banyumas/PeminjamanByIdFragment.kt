package com.perpus.banyumas

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpus.banyumas.adapter.PinjamByIdAdapter
import com.perpus.banyumas.data.response.*
import com.perpus.banyumas.databinding.FragmentHomeBinding
import com.perpus.banyumas.databinding.FragmentPeminjamanByIdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeminjamanByIdFragment : Fragment(), PinjamByIdAdapter.ListPinjamInterface {

    private var _binding: FragmentPeminjamanByIdBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        _binding = FragmentPeminjamanByIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PinjamByIdAdapter(this)
        profileViewModel.getId().observe(viewLifecycleOwner) {
            viewModel.getPinjamById(it)
        }
        viewModel.pinjamid.observe(viewLifecycleOwner){
            if(it != null){
                adapter.setData(it.data)
            }
        }
        binding.rvPinjam.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPinjam.adapter = adapter
    }

    override fun pinjam(idpinjam: String) {
        val bundle = Bundle()
        bundle.putString("idpinjam", idpinjam)
        findNavController().navigate(R.id.action_peminjamanByIdFragment_to_detailPeminjamanByIdFragment,bundle)

    }

}