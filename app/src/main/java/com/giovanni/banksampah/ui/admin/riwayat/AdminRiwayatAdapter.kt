package com.giovanni.banksampah.ui.admin.riwayat

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.giovanni.banksampah.databinding.ItemPermintaanBinding
import com.giovanni.banksampah.helper.Helper
import com.giovanni.banksampah.model.Model
import com.giovanni.banksampah.ui.admin.daftarpermintaan.DaftarPermintaanAdapter

class AdminRiwayatAdapter(val listPermintaan: List<Model>): RecyclerView.Adapter<AdminRiwayatAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemPermintaanBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPermintaanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
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
            tvAlamat.text = permintaan.alamat
            btnTerima.visibility = View.GONE
            btnTolak.visibility = View.GONE
            btnVerifikasi.visibility = View.GONE
            btnBatal.visibility = View.GONE
            tvStatus.text = permintaan.status
            tvStatus.setTextColor(Color.GREEN)
        }
    }
}