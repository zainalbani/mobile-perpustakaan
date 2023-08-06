package com.perpus.banyumas

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.data.response.PasswordResponse
import com.perpus.banyumas.data.response.PinjamResponse
import com.perpus.banyumas.databinding.FragmentPinjamBinding
import com.perpus.banyumas.databinding.FragmentScannerBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PinjamFragment : Fragment() {
    private var _binding: FragmentPinjamBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentPinjamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTanggal()

        profileViewModel.getId().observe(viewLifecycleOwner) {
            binding.idAnggota.setText(it)
        }


        binding.btnPinjam.setOnClickListener {
            val idAnggota = binding.idAnggota.text.toString()
            val idPinjam = binding.idPinjam.text.toString()
            val idPetugas = binding.idPetugas.text.toString()
            val tglPinjam = binding.tglPinjam.text.toString()

            viewModel.postPinjam(idPinjam, tglPinjam, idAnggota, idPetugas)
            val bundle = Bundle()
            bundle.putString("idPinjam", idPinjam)
            findNavController().navigate(R.id.action_pinjamFragment_to_detailPinjamFragment,bundle)
        }

    }

    private fun getTanggal() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                // Aksi yang ingin Anda lakukan ketika tanggal dipilih
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                binding.tglPinjam.setText(selectedDate)
            },
            year,
            month,
            day
        )


        binding.tglPinjam.setOnClickListener {
            datePickerDialog.show()
        }
    }


}