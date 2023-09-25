package com.perpus.banyumas.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.perpus.banyumas.viewmodel.BookViewModel
import com.perpus.banyumas.databinding.FragmentDetailPeminjamanByIdBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

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
            binding.tvIdBuku.text = "ISSN : " + it!!.data.idbuku
            binding.tvIdPinjam.text = "Id Pinjam : " + it.data.idpinjam
            binding.tvTglPinjam.text = "Tanggal Pinjam : " + it.data.pinjam.tglpinjam

            val tglPinjam = it.data.pinjam.tglpinjam
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val datePinjam = LocalDate.parse(tglPinjam, formatter)
            val dateNow = LocalDate.now()

            val selisih = datePinjam.until(dateNow).days
            Log.d(TAG, "onViewCreated: ${selisih}")

            if(selisih > 7){
                val operasi = selisih - 7
                val jumlahDenda = operasi * 500

                binding.tvDenda.text = "Denda : Rp." + jumlahDenda.toString()
            }
            else {
                binding.tvDenda.text = "Denda : Rp. 0"
            }

        }


    }
}