package com.perpus.banyumas

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.perpus.banyumas.databinding.FragmentScannerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerFragment : Fragment() {
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel
    private lateinit var codeScanner : CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        _binding = FragmentScannerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPermission()
        QRScanner()
        reScan()
        toPinjam()

        binding.cardRescan.setOnClickListener {
            reScan()
        }
    }

    private fun toPinjam() {
        binding.btnPinjam.setOnClickListener {
            findNavController().navigate(R.id.action_scannerFragment_to_pinjamFragment)
        }
    }

    private fun reScan() {
        codeScanner.startPreview()
        binding.textQR.text="scanning..."
    }

    private fun QRScanner() {
        codeScanner  = CodeScanner(requireContext(), binding.scanView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
            decodeCallback = DecodeCallback {
                activity?.runOnUiThread{
                    binding.textQR.text = it.text
                    viewModel.saveIdBook(it.text)

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
        val permission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), 101)
        }
    }

}