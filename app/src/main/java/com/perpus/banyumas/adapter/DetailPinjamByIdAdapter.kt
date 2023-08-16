package com.perpus.banyumas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perpus.banyumas.data.response.DataDetPinjamById
import com.perpus.banyumas.data.response.DataDetailPinjam
import com.perpus.banyumas.data.response.DataPinjamById
import com.perpus.banyumas.databinding.DetpinjamListBinding
import com.perpus.banyumas.databinding.PinjamListBinding

class DetailPinjamByIdAdapter (private var itemClick: DetailPinjamByIdAdapter.ListPinjamInterface) : RecyclerView.Adapter<DetailPinjamByIdAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataDetPinjamById>(){
        override fun areItemsTheSame(
            oldItem: DataDetPinjamById,
            newItem: DataDetPinjamById
        ): Boolean {
            return oldItem.iddetpinjam == oldItem.iddetpinjam
        }

        override fun areContentsTheSame(
            oldItem: DataDetPinjamById,
            newItem: DataDetPinjamById
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: DetpinjamListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataDetPinjamById) {
            with(item) {


                binding.tvIdPinjam.text = item.idpinjam
                binding.tvIdBuku.text = item.idbuku
                binding.tvStatus.text = item.status

                binding.card.setOnClickListener{
                    itemClick.pinjam(item.idbuku)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DetpinjamListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pinjam = differ.currentList[position]
        holder.bind(pinjam)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataDetPinjamById>){
        differ.submitList(data)
    }

    interface ListPinjamInterface {
        fun pinjam(idbuku: String)
    }
}