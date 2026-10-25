package id.ac.polbeng.akshaandestian.kabkotaretrofitapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.adapter.KabKotaAdapter
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.databinding.ActivityMainBinding
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.helpers.Config
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.model.KabKotaItem
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.response.KabKotaResponse
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvKabKota.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(
            this,
            layoutManager.orientation
        )

        binding.rvKabKota.addItemDecoration(itemDecoration)

        // Ambil data saat aplikasi pertama kali dibuka
        getKabKotaList()

        // Swipe untuk refresh data
        binding.swipeRefresh.setOnRefreshListener {
            getKabKotaList()
        }
    }

    private fun getKabKotaList() {

        showLoading(true)

        val client = ApiConfig
            .getApiService()
            .getListUsers()

        client.enqueue(object : Callback<KabKotaResponse> {

            override fun onResponse(
                call: Call<KabKotaResponse>,
                response: Response<KabKotaResponse>
            ) {

                showLoading(false)

                if (response.isSuccessful) {

                    val responseBody = response.body()

                    if (responseBody != null) {
                        setKabKotaData(responseBody.data)
                    }

                } else {

                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}"
                    )
                }
            }

            override fun onFailure(
                call: Call<KabKotaResponse>,
                t: Throwable
            ) {

                showLoading(false)

                Log.e(
                    TAG,
                    "onFailure: ${t.message}"
                )
            }
        })
    }

    private fun setKabKotaData(
        kabKota: List<KabKotaItem>
    ) {

        val adapter = KabKotaAdapter()

        adapter.submitList(kabKota)

        binding.rvKabKota.adapter = adapter

        // Aksi ketika item Kabupaten/Kota diklik
        adapter.setOnItemClickCallback(
            object : KabKotaAdapter.OnItemClickCallback {

                override fun onItemClicked(
                    data: KabKotaItem
                ) {

                    val json = Gson().toJson(data)

                    val moveWithObjectIntent =
                        Intent(
                            this@MainActivity,
                            DetailKabKotaActivity::class.java
                        )

                    moveWithObjectIntent.putExtra(
                        Config.EXTRA_KAB_KOTA,
                        json
                    )

                    startActivity(moveWithObjectIntent)
                }
            }
        )
    }

    private fun showLoading(
        isLoading: Boolean
    ) {

        // ProgressBar tidak ditampilkan ketika
        // SwipeRefresh sedang aktif
        if (!binding.swipeRefresh.isRefreshing) {

            binding.progressBar.visibility =
                if (isLoading) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        // Matikan indikator SwipeRefresh
        // setelah proses selesai
        if (!isLoading) {
            binding.swipeRefresh.isRefreshing = false
        }
    }
}