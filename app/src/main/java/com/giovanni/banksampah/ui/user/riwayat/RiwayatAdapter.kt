package com.giovanni.banksampah.ui.user.riwayat

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giovanni.banksampah.databinding.ItemRiwayatBinding
import com.giovanni.banksampah.helper.Helper.rupiahFormat
import com.giovanni.banksampah.model.Model

class RiwayatAdapter(val listRiwayat: List<Model>, private val viewModel: RiwayatViewModel): RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listRiwayat.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val riwayat = listRiwayat[position]
        holder.binding.apply {
            tvNama.text = riwayat.namaPengguna
            tvDate.text = riwayat.tanggal
            tvKategori.text = "Sampah " + riwayat.jenisSampah
            tvBerat.text = "Berat : " + riwayat.berat.toString() + " Kg"
            tvSaldo.text = "Pendapatan : " + rupiahFormat(riwayat.harga)
            tvStatus.text = riwayat.status

            if (riwayat.status == "Diproses") {
                tvStatus.setTextColor(Color.parseColor("#f1c232"))
            } else if (riwayat.status == "Diterima") {
                tvStatus.setTextColor(Color.GREEN)
            } else {
                tvStatus.setTextColor(Color.RED)
            }

            imageDelete.setOnClickListener{
                viewModel.deleteData(riwayat.uid)
            }
        }
    }

    class ViewHolder(var binding: ItemRiwayatBinding): RecyclerView.ViewHolder(binding.root)
}