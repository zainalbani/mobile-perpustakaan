package com.perpus.banyumas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perpus.banyumas.data.response.DataSearch
import com.perpus.banyumas.databinding.BukuListBinding

class SearchBookAdapter(private var itemClick: SearchBookInterface) : RecyclerView.Adapter<SearchBookAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataSearch>(){
        override fun areItemsTheSame(
            oldItem: DataSearch,
            newItem: DataSearch
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: DataSearch,
            newItem: DataSearch
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: BukuListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataSearch) {
            with(binding) {

                tvIdBuku.text = item.idbuku
                tvTitle.text = item.judul
                tvPenerbit.text = item.penerbit
                tvPengarang.text = item.pengarang
                itemView.setOnClickListener {
                    itemClick.idbuku(item.idbuku)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BukuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val buku = differ.currentList[position]
        holder.bind(buku)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setData(data : List<DataSearch>){
        differ.submitList(data)
    }
    interface SearchBookInterface {
        fun idbuku(idbuku: String)
    }
}