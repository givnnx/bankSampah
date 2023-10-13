package com.giovanni.banksampah.ui.admin.daftarpermintaan

import android.content.Context
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

class DaftarPermintaanAdapter(val listPermintaan: List<Model>, private val viewModel: DaftarPermintaanViewModel, private val context: Context): RecyclerView.Adapter<DaftarPermintaanAdapter.ViewHolder>() {

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


            btnTerima.setOnClickListener {
                permintaan.status = "Diproses"
                viewModel.updateDataTerima(permintaan.status, permintaan.jenisSampah, permintaan.uid, permintaan.namaPengguna)
                viewModel.state.observe(context as LifecycleOwner) {
                    if (it == 1) {
                        btnTerima.visibility = View.GONE
                        btnTolak.visibility = View.GONE
                        btnVerifikasi.visibility = View.VISIBLE
                        btnBatal.visibility = View.VISIBLE
                        tvStatus.text = permintaan.status
                        tvStatus.setTextColor(Color.parseColor("#f1c232"))
                    }
                }
            }

            btnBatal.setOnClickListener {
                permintaan.status = "Belum diterima"
                viewModel.updateDataTerima(permintaan.status, permintaan.jenisSampah, permintaan.uid, permintaan.namaPengguna)
                viewModel.state.observe(context as LifecycleOwner) {
                    if (it == 1) {
                        btnTerima.visibility = View.VISIBLE
                        btnTolak.visibility = View.VISIBLE
                        btnVerifikasi.visibility = View.GONE
                        btnBatal.visibility = View.GONE
                        tvStatus.setTextColor(Color.RED)
                        tvStatus.text = permintaan.status
                    }
                }
            }

            btnVerifikasi.setOnClickListener {
                permintaan.status = "Diterima"
                viewModel.updateDataTerima(permintaan.status, permintaan.jenisSampah, permintaan.uid, permintaan.namaPengguna)
                viewModel.state.observe(context as LifecycleOwner) {
                    if (it == 1) {
                        btnTerima.visibility = View.GONE
                        btnTolak.visibility = View.GONE
                        btnVerifikasi.visibility = View.GONE
                        btnBatal.visibility = View.GONE
                        tvStatus.text = permintaan.status
                        tvStatus.setTextColor(Color.GREEN)
                        Log.d("adapter_id", permintaan.idPengguna)
                        viewModel.updateSaldo(permintaan.idPengguna, permintaan.harga.toLong())
                    }
                }
            }
        }
    }
}