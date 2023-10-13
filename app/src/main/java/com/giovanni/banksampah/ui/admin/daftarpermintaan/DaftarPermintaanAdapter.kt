package com.giovanni.banksampah.ui.admin.daftarpermintaan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giovanni.banksampah.databinding.ItemPermintaanBinding
import com.giovanni.banksampah.helper.Helper
import com.giovanni.banksampah.model.Model

class DaftarPermintaanAdapter(val listPermintaan: List<Model>, private val viewModel: DaftarPermintaanViewModel): RecyclerView.Adapter<DaftarPermintaanAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemPermintaanBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarPermintaanAdapter.ViewHolder {
        val binding = ItemPermintaanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DaftarPermintaanAdapter.ViewHolder(binding)
    }

    override fun getItemCount() = listPermintaan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val permintaan = listPermintaan[position]
        holder.binding.apply {
            tvNama.text = permintaan.namaPengguna
            tvDate.text = permintaan.tanggal
            tvKategori.text = "Sampah " + permintaan.jenisSampah
            tvBerat.text = "Berat : " + permintaan.berat.toString() + " Kg"
            tvSaldo.text = "Pendapatan : " + Helper.rupiahFormat(permintaan.harga)
        }
    }
}