package com.giovanni.banksampah.ui.user.inputdata

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.giovanni.banksampah.R
import com.giovanni.banksampah.databinding.ActivityInputDataBinding
import com.giovanni.banksampah.helper.Helper.rupiahFormat
import com.giovanni.banksampah.model.UserPreference
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class InputDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputDataBinding
    private lateinit var viewModel: InputDataViewModel
    lateinit var strKategoriSelected: String
    lateinit var strKategori: Array<String>
    lateinit var strHarga: Array<String>
    var harga = 0
    private var calTotal = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = getViewModel(this@InputDataActivity, dataStore)
        setAction()
        setToolbar()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getViewModel (activity: AppCompatActivity, dataStore: DataStore<Preferences>): InputDataViewModel {
        val factory = ViewModelFactoryInputData(activity.application, UserPreference.getInstance(dataStore))
        return ViewModelProvider(activity, factory)[InputDataViewModel::class.java]
    }

    private fun setAction(){
        strKategori = resources.getStringArray(R.array.kategori_sampah)
        strHarga = resources.getStringArray(R.array.harga_perkilo)

        val adapter = ArrayAdapter(this@InputDataActivity, android.R.layout.simple_spinner_item, strKategori)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spKategori.adapter = adapter

        binding.apply {
            spKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    strKategoriSelected = strKategori[position]
                    harga = strHarga[position].toInt()
                    // Do something with the selected language
                    when (position){
                        0 -> {
                            inputHarga.hint = "Rp. ${strHarga[position]}"
                        }
                        1 -> {
                            inputHarga.hint = "Rp. ${strHarga[position]}"
                        }
                        2 -> {
                            inputHarga.hint = "Rp. ${strHarga[position]}"
                        }
                        3 -> {
                            inputHarga.hint = "Rp. ${strHarga[position]}"
                        }
                        4 -> {
                            inputHarga.hint = "Rp. ${strHarga[position]}"
                        }
                        5 -> {
                            inputHarga.hint = "Rp. ${strHarga[position]}"
                        }
                        6 -> {
                            inputHarga.hint = "Rp. ${strHarga[position]}"
                        }

                    }

                    if (inputBerat.text.toString() != ""){
                        val berat = inputBerat.text.toString().toInt()
                        calTotal(berat)
                    }
                    // For example, display a toast with the selected language
                    Toast.makeText(this@InputDataActivity, "Selected Item: $strKategoriSelected", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }

            inputBerat.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // This method is called before the text is changed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // This method is called while the text is changing
                }

                override fun afterTextChanged(s: Editable?) {
                    // This method is called after the text has been changed
                    if (inputBerat.text.toString() != ""){
                        val berat = inputBerat.text.toString().toInt()
                        calTotal(berat)
                    }
                }
            })

            inputTanggal.setOnClickListener {
                val tanggalJemput = Calendar.getInstance()
                val date =
                    DatePickerDialog.OnDateSetListener { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                        tanggalJemput[Calendar.YEAR] = year
                        tanggalJemput[Calendar.MONTH] = monthOfYear
                        tanggalJemput[Calendar.DAY_OF_MONTH] = dayOfMonth
                        val strFormatDefault = "d MMMM yyyy"
                        val simpleDateFormat =
                            SimpleDateFormat(strFormatDefault, Locale.getDefault())
                        inputTanggal.setText(simpleDateFormat.format(tanggalJemput.time))
                    }
                DatePickerDialog(
                    this@InputDataActivity, date,
                    tanggalJemput[Calendar.YEAR],
                    tanggalJemput[Calendar.MONTH],
                    tanggalJemput[Calendar.DAY_OF_MONTH]
                ).show()
            }

            btnCheckout.setOnClickListener{
                val kategoriSampah = strKategoriSelected
                val berat = inputBerat.text.toString().toInt()
                val harga = calTotal
                val tanggal = inputTanggal.text.toString()
                val alamat = inputAlamat.text.toString()
                val catatan = inputTambahan.text.toString()
                var nama = "Nama"
                val status = "Belum diterima"
                viewModel.getUser().observe(this@InputDataActivity) {
                    nama = it.username
                }
                viewModel.addOrder(nama, kategoriSampah, berat, harga, tanggal, alamat, catatan, this@InputDataActivity, status)
            }
        }
    }

    private fun calTotal(berat: Int){
        calTotal = harga * berat
        binding.inputHarga.setText(rupiahFormat(calTotal))
    }
}