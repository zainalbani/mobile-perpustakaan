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
import com.perpus.banyumas.R
import com.perpus.banyumas.adapter.BukuAdapter
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.databinding.FragmentKoleksiBinding
import com.perpus.banyumas.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KoleksiFragment : Fragment(), BukuAdapter.ListBukuInterface {
    private var _binding: FragmentKoleksiBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentKoleksiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

    }

    private fun setAdapter() {
        viewModel.getAllBook()
        viewModel.bukuResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val adapter = BukuAdapter(this)
                    binding.rvBuku.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvBuku.adapter = adapter
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

    override fun buku(idbuku: String) {
        val bundle = Bundle()
        bundle.putString("idbuku", idbuku)
        findNavController().navigate(
            R.id.action_koleksiFragment_to_detailBukuFragment,
            bundle
        )

    }

}