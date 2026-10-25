package id.ac.polbeng.akshaandestian.kabkotaretrofitapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.databinding.ActivityDetailKabKotaBinding
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.helpers.Config
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.model.KabKotaItem

class DetailKabKotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKabKotaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailKabKotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val json = intent.getStringExtra(Config.EXTRA_KAB_KOTA)

        if (json != null) {

            val dataKabKota =
                Gson().fromJson(json, KabKotaItem::class.java)

            val url = "${Config.LOGO_URL}/${dataKabKota.logo}"

            Glide.with(applicationContext)
                .load(url)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.imgItemPhoto)

            binding.tvNama.text =
                dataKabKota.kabupatenKota

            binding.tvPusatPemerintahan.text =
                dataKabKota.pusatPemerintahan

            binding.tvBupatiWalikota.text =
                dataKabKota.bupatiWalikota

            binding.tvTanggalBerdiri.text =
                dataKabKota.tanggalBerdiri

            binding.tvLuasWilayah.text =
                dataKabKota.luasWilayah.toString()

            binding.tvJumlahPenduduk.text =
                dataKabKota.jumlahPenduduk.toString()

            binding.tvJumlahKecamatan.text =
                dataKabKota.jumlahKecamatan.toString()

            binding.tvJumlahKelurahan.text =
                dataKabKota.jumlahKelurahan.toString()

            binding.tvJumlahDesa.text =
                dataKabKota.jumlahDesa.toString()

            binding.tvDeskripsi.text =
                dataKabKota.deskripsi

            // Tombol untuk menampilkan peta wilayah
            binding.btnViewPeta.setOnClickListener {

                val showViewPeta =
                    Intent(
                        this@DetailKabKotaActivity,
                        ViewPetaActivity::class.java
                    )

                showViewPeta.putExtra(
                    Config.EXTRA_URL_PETA,
                    dataKabKota.urlPetaWilayah
                )

                startActivity(showViewPeta)
            }
        }
    }
}