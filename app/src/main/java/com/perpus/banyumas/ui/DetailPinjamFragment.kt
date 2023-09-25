package com.perpus.banyumas.ui

import android.app.AlertDialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.perpus.banyumas.viewmodel.BookViewModel
import com.perpus.banyumas.viewmodel.ProfileViewModel
import com.perpus.banyumas.R
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.data.response.PinjamResponse
import com.perpus.banyumas.databinding.FragmentDetailPinjamBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class DetailPinjamFragment : DialogFragment() {

    private var _binding: FragmentDetailPinjamBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private var kodeBaru = ""

    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentDetailPinjamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idPinjam.isEnabled = false
        binding.idAnggota.isEnabled = false
        binding.idJudulBuku.isEnabled = false
        binding.idBuku.isEnabled = false
        val pinjam = arguments?.getString("idPinjam")
        if (pinjam == "null") {
            kodeBaru = "001"
        } else {
            val charSequence = pinjam?.subSequence(9, pinjam.length)
            var substring = charSequence.toString().toInt()
            substring += 1
            val noBaru = substring.toString()
            val jumlahChar = noBaru.length

            if (jumlahChar == 1) {
                kodeBaru = "00" + noBaru
            } else if (jumlahChar == 2) {
                kodeBaru = "0" + noBaru
            } else {
                kodeBaru = noBaru
            }
        }
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val formattedDate = dateFormat.format(currentDate).toString()

        val idPinjamBaru = formattedDate + kodeBaru
        binding.idPinjam.setText(idPinjamBaru)
        Log.d(ContentValues.TAG, "onViewCreated: ${idPinjamBaru}")
        profileViewModel.getId().observe(viewLifecycleOwner) {
            binding.idAnggota.setText(it)
        }
        viewModel.getIdBook().observe(viewLifecycleOwner) { data ->
            viewModel.getBookById(data)
            viewModel.getbuku.observe(viewLifecycleOwner) {
                binding.idJudulBuku.setText(it?.data?.judul.toString())
            }
            binding.idBuku.setText(data)
        }
        processCreate()

        binding.btnPinjam.setOnClickListener {
            val idAnggota = binding.idAnggota.text.toString()
            val idBuku = binding.idBuku.text.toString()
            val idPinjam = binding.idPinjam.text.toString()

            viewModel.postPinjam(idPinjam, idBuku, idAnggota)

        }
    }

    private fun processCreate() {
        viewModel.pinjamResult.observe(viewLifecycleOwner) {
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
    }

    private fun processUpdate(data: PinjamResponse?) {
        snackBar("${data?.message}", "Success")
        findNavController().navigate(R.id.action_detailPinjamFragment_to_homeFragment)
        viewModel.removeIdBook()
    }

    private fun processError(msg: String?) {
        snackBar("$msg", "Error")
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