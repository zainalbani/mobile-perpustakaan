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
import com.perpus.banyumas.adapter.SearchBookAdapter
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.databinding.FragmentSearchBinding
import com.perpus.banyumas.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchBookAdapter.SearchBookInterface {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.text = searchView.text
                searchView.hide()
                val search = searchView.text.toString()
                viewModel.searchBook(search)
                false
            }
        }
        viewModel.searchBookResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvSearch.visibility = View.VISIBLE
                    val adapter = SearchBookAdapter(this)
                    binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvSearch.adapter = adapter
                    adapter.setData(it.data!!.data)

                }

                is BaseResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvSearch.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${it.msg}", Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
        }
    }

    override fun idbuku(idbuku: String) {
        val bundle = Bundle()
        bundle.putString("idbuku", idbuku)
        findNavController().navigate(R.id.action_searchFragment_to_detailBukuFragment, bundle)
    }
}