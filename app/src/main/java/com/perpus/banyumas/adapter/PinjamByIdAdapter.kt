package com.perpus.banyumas.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perpus.banyumas.data.response.DataPinjamById
import com.perpus.banyumas.databinding.PinjamListBinding

class PinjamByIdAdapter (private var itemClick: PinjamByIdAdapter.ListPinjamInterface) : RecyclerView.Adapter<PinjamByIdAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataPinjamById>(){
        override fun areItemsTheSame(
            oldItem: DataPinjamById,
            newItem: DataPinjamById
        ): Boolean {
            return oldItem.idpinjam == oldItem.idpinjam
        }

        override fun areContentsTheSame(
            oldItem: DataPinjamById,
            newItem: DataPinjamById
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: PinjamListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataPinjamById) {
            with(item) {


                binding.tvIdPinjam.text = item.idpinjam
                binding.tvTglPinjam.text = item.tglpinjam
                binding.tvStatus.text = item.status

                binding.card.setOnClickListener{
                    itemClick.pinjam(item.idpinjam)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PinjamListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pinjam = differ.currentList[position]
        holder.bind(pinjam)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataPinjamById>){
        differ.submitList(data)
    }

    interface ListPinjamInterface {
        fun pinjam(idpinjam: String)
    }
}