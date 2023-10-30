package com.giovanni.banksampah.ui.admin.daftarmintatariksaldo

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.giovanni.banksampah.databinding.ItemPermintaanTariksaldoBinding
import com.giovanni.banksampah.helper.Helper
import com.giovanni.banksampah.model.TarikSaldoModel

class DaftarPermintaanTarikSaldoAdapter(private val listPermintaanTarikSaldo: List<TarikSaldoModel>, private val viewModel: DaftarPermintaanTarikSaldoViewModel, private val context: Context): RecyclerView.Adapter<DaftarPermintaanTarikSaldoAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemPermintaanTariksaldoBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPermintaanTariksaldoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listPermintaanTarikSaldo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val permintaan = listPermintaanTarikSaldo[position]
        holder.binding.apply {
            tvNama.text = permintaan.username
            tvDate.text = permintaan.tanggal
            tvStatus.text = permintaan.status
            tvSaldoSisa.text = Helper.rupiahFormat(permintaan.saldo.toInt())
            tvjmlPenarikan.text = Helper.rupiahFormat(permintaan.jumlah.toInt())

            fun diterima(){
                btnTerima.visibility = View.GONE
                tvStatus.text = permintaan.status
                tvStatus.setTextColor(Color.GREEN)
                Log.d("adapter_id", permintaan.idPengguna)
            }

            fun belumDiterima(){
                btnTerima.visibility = View.VISIBLE
                tvStatus.setTextColor(Color.RED)
                tvStatus.text = permintaan.status
            }

            if (tvStatus.text == "Diterima"){
                diterima()
            } else {
                belumDiterima()
            }

            fun showLoading(isLoading: Boolean){
                if (isLoading) {
                    btnTerima.isEnabled = false
                    pbSignin.visibility = View.VISIBLE
                } else {
                    btnTerima.isEnabled = true
                    pbSignin.visibility = View.GONE
                }
            }

            viewModel.isLoading.observe(context as LifecycleOwner){
                showLoading(it)
            }

            btnTerima.setOnClickListener {
                permintaan.status = "Diterima"
                viewModel.state.observe(context as LifecycleOwner) {
                    if (it == 1) {
                        viewModel.updateSaldo(permintaan.idPengguna, permintaan.jumlah, permintaan.uid)
                        diterima()
                    }
                }
            }
        }
    }

}