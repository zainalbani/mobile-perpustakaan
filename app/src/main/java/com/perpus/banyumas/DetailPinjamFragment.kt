package com.perpus.banyumas

import android.app.AlertDialog
import android.content.ContentValues.TAG
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
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.data.response.PinjamResponse
import com.perpus.banyumas.databinding.FragmentDetailPinjamBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPinjamFragment : DialogFragment() {

    private var _binding: FragmentDetailPinjamBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel
    private lateinit var profileViewModel: ProfileViewModel

    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentDetailPinjamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnPinjam.setOnClickListener {
            val idPinjam = arguments?.getString("idPinjam")
            Log.d(TAG, "onViewCreated:${idPinjam.toString()}")
            val jmlBuku = binding.etJmlBuku.text.toString()

            viewModel.getIdBook().observe(viewLifecycleOwner) {data ->
                viewModel.postDetailPinjam(data, idPinjam.toString(), jmlBuku)
            }
            findNavController().navigate(R.id.action_detailPinjamFragment_to_homeFragment)
            viewModel.removeIdBook()

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Success")
            builder.setMessage("Peminjaman Berhasil")

            builder.setPositiveButton("OK") { dialog, which ->

            }
            val dialog = builder.create()
            dialog.show()
        }
    }

}