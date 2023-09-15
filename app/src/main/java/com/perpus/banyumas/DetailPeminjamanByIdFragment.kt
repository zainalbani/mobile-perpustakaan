package com.perpus.banyumas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.perpus.banyumas.data.response.*
import com.perpus.banyumas.databinding.FragmentDetailPeminjamanByIdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPeminjamanByIdFragment : Fragment(){

    private var _binding: FragmentDetailPeminjamanByIdBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        _binding = FragmentDetailPeminjamanByIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idPinjam = arguments?.getString("idpinjam")

        if (idPinjam != null) {
            viewModel.getDetailPinjamById(idPinjam)
        }
        viewModel.detpinjamid.observe(viewLifecycleOwner){
            binding.tvName.text = it?.data?.buku?.judul.toString()
            binding.tvIdPinjam.text = "Id Pinjam : " + it?.data?.idpinjam.toString()
            binding.tvJmlBuku.text = "Jumlah Buku : " + it?.data?.jml_buku.toString()
            binding.tvIdBuku.text = "Id Buku : " + it?.data?.idbuku.toString()
            binding.tvDenda.text = "Denda : Rp. " + it?.data?.pinjam?.total_denda.toString()
        }
    }
}