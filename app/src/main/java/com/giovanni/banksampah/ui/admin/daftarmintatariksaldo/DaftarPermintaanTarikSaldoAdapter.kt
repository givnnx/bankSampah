package com.giovanni.banksampah.ui.admin.daftarmintatariksaldo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giovanni.banksampah.databinding.ItemPermintaanTariksaldoBinding
import com.giovanni.banksampah.helper.Helper
import com.giovanni.banksampah.model.Model

class DaftarPermintaanTarikSaldoAdapter(val listPermintaanTarikSaldo: List<Model>, private val viewModel: DaftarPermintaanTarikSaldoViewModel, private val context: Context): RecyclerView.Adapter<DaftarPermintaanTarikSaldoAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemPermintaanTariksaldoBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPermintaanTariksaldoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listPermintaanTarikSaldo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val permintaan = listPermintaanTarikSaldo[position]
        holder.binding.apply {
            tvNama.text = permintaan.namaPengguna
            tvDate.text = permintaan.tanggal
            tvStatus.text = permintaan.status
        }
    }

}