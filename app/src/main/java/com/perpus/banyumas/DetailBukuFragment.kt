package com.perpus.banyumas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.perpus.banyumas.databinding.FragmentDetailBukuBinding
import com.perpus.banyumas.databinding.FragmentDetailPeminjamanByIdBinding
import com.perpus.banyumas.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBukuFragment : Fragment() {

    private var _binding : FragmentDetailBukuBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        _binding = FragmentDetailBukuBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idBuku = arguments?.getString("idbuku")

        if(idBuku != null){
            viewModel.getDetailBuku(idBuku)
        }
        viewModel.detailbuku.observe(viewLifecycleOwner){
            binding.tvName.text = it!!.data.judul
            binding.tvIdBuku.text = "ISSN : " + it.data.idbuku
            binding.tvPenerbitBuku.text = "Penerbit : " + it.data.penerbit
            binding.tvKategoriBuku.text = "Kategori : " + it.data.idkatbuku
            binding.tvRakBuku.text = "Rak : " + it.data.idrak
        }
    }
}