package com.perpus.banyumas.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.perpus.banyumas.viewmodel.BookViewModel
import com.perpus.banyumas.viewmodel.ProfileViewModel
import com.perpus.banyumas.R
import com.perpus.banyumas.databinding.FragmentScannerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerFragment : Fragment() {
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPermission()
        QRScanner()
        reScan()

        binding.btnPinjam.setOnClickListener {
            Toast.makeText(requireContext(), "Error: SCAN QR terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
        binding.cardRescan.setOnClickListener {
            reScan()
        }
    }

    private fun toPinjam() {
        viewModel.getIdPinjam()
        binding.btnPinjam.setOnClickListener {
            viewModel.getpinjam.observe(viewLifecycleOwner) {
                val bundle = Bundle()
                bundle.putString("idPinjam", it?.data?.idpinjam.toString())
                if (it != null) {
                    findNavController().navigate(
                        R.id.action_scannerFragment_to_detailPinjamFragment,
                        bundle
                    )
                }
            }
        }
    }

    private fun reScan() {
        codeScanner.startPreview()
        binding.textQR.text = "scanning..."
    }

    private fun QRScanner() {
        codeScanner = CodeScanner(requireContext(), binding.scanView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
            decodeCallback = DecodeCallback {
                activity?.runOnUiThread {
                    binding.textQR.text = it.text
                    viewModel.saveIdBook(it.text)
                    toPinjam()
                }
            }
            errorCallback = ErrorCallback {
                activity?.runOnUiThread {
                }
            }
            binding.scanView.setOnClickListener {
                reScan()
            }
        }
    }

    private fun getPermission() {
        val permission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                101
            )
        }
    }
}