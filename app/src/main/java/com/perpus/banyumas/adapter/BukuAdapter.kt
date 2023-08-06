package com.perpus.banyumas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perpus.banyumas.data.response.DataXX
import com.perpus.banyumas.databinding.BukuListBinding

class BukuAdapter : RecyclerView.Adapter<BukuAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataXX>(){
        override fun areItemsTheSame(
            oldItem: DataXX,
            newItem: DataXX
        ): Boolean {
            return oldItem.idbuku == oldItem.idbuku
        }

        override fun areContentsTheSame(
            oldItem: DataXX,
            newItem: DataXX
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: BukuListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataXX) {
            with(item) {

                binding.tvIdBuku.text = item.idbuku
                binding.tvTitle.text = item.judul
                binding.tvPenerbit.text = item.penerbit
                binding.tvPengarang.text = item.pengarang


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
    fun setData(data : List<DataXX>){
        differ.submitList(data)
    }
}