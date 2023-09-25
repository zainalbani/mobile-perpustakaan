package com.perpus.banyumas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpus.banyumas.viewmodel.BookViewModel
import com.perpus.banyumas.viewmodel.ProfileViewModel
import com.perpus.banyumas.R
import com.perpus.banyumas.adapter.PinjamByIdAdapter
import com.perpus.banyumas.data.response.BaseResponse
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
        profileViewModel.getId().observe(viewLifecycleOwner) {
            viewModel.getPinjamById(it)
        }
        viewModel.pinjamid.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val adapter = PinjamByIdAdapter(this)
                    binding.rvPinjam.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.rvPinjam.adapter = adapter
                    adapter.setData(it.data!!.data)
                }

                is BaseResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${it.msg}", Toast.LENGTH_SHORT).show()

                }

                else -> {

                }

            }
        }

    }

    override fun pinjam(idpinjam: String) {
        val bundle = Bundle()
        bundle.putString("idpinjam", idpinjam)
        findNavController().navigate(
            R.id.action_peminjamanByIdFragment_to_detailPeminjamanByIdFragment,
            bundle
        )

    }

}