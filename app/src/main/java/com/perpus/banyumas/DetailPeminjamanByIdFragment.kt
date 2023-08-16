package com.perpus.banyumas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpus.banyumas.adapter.DetailPinjamByIdAdapter
import com.perpus.banyumas.adapter.PinjamByIdAdapter
import com.perpus.banyumas.data.response.*
import com.perpus.banyumas.databinding.FragmentDetailPeminjamanByIdBinding
import com.perpus.banyumas.databinding.FragmentPeminjamanByIdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPeminjamanByIdFragment : Fragment(), DetailPinjamByIdAdapter.ListPinjamInterface {

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

        val adapter = DetailPinjamByIdAdapter(this)
        val idPinjam = arguments?.getString("idpinjam")

        if (idPinjam != null) {
            viewModel.getDetailPinjamById(idPinjam)
        }
        viewModel.detpinjamid.observe(viewLifecycleOwner){
            if(it != null){
                adapter.setData(it.data)
            }
        }
        binding.rvDetPinjam.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvDetPinjam.adapter = adapter
    }

    override fun pinjam(idbuku: String) {
        val bundle = Bundle()
        bundle.putString("idbuku", idbuku)
        findNavController().navigate(R.id.action_homeFragment_to_peminjamanByIdFragment,bundle)

    }

}