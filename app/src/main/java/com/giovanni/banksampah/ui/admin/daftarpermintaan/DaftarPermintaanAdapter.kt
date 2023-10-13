package com.giovanni.banksampah.ui.admin.daftarpermintaan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giovanni.banksampah.databinding.ItemPermintaanBinding
import com.giovanni.banksampah.helper.Helper
import com.giovanni.banksampah.model.Model

class DaftarPermintaanAdapter(val listPermintaan: List<Model>, private val viewModel: DaftarPermintaanViewModel): RecyclerView.Adapter<DaftarPermintaanAdapter.ViewHolder>() {

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

            btnTerima.setOnClickListener {
                btnTerima.visibility = View.GONE
                btnTolak.visibility = View.GONE
                btnVerifikasi.visibility = View.VISIBLE
                btnBatal.visibility = View.VISIBLE

                permintaan.status = "Diproses"
                viewModel.updateDataTerima(permintaan.status, permintaan.jenisSampah, permintaan.uid, permintaan.namaPengguna)
            }

            btnBatal.setOnClickListener {
                btnTerima.visibility = View.VISIBLE
                btnTolak.visibility = View.VISIBLE
                btnVerifikasi.visibility = View.GONE
                btnBatal.visibility = View.GONE

                permintaan.status = "Belum diterima"
                viewModel.updateDataTerima(permintaan.status, permintaan.jenisSampah, permintaan.uid, permintaan.namaPengguna)
            }

            btnVerifikasi.setOnClickListener {
                btnTerima.visibility = View.GONE
                btnTolak.visibility = View.GONE
                btnVerifikasi.visibility = View.GONE
                btnBatal.visibility = View.GONE

                permintaan.status = "Diterima"
                viewModel.updateDataTerima(permintaan.status, permintaan.jenisSampah, permintaan.uid, permintaan.namaPengguna)
            }
        }
    }
}