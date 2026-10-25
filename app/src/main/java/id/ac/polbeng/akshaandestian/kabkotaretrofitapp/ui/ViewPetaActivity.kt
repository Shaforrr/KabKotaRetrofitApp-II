package id.ac.polbeng.akshaandestian.kabkotaretrofitapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.databinding.ActivityViewPetaBinding
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.helpers.Config

class ViewPetaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPetaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewPetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val urlPeta = intent.getStringExtra(
            Config.EXTRA_URL_PETA
        )

        val requestBuilder = GlideToVectorYou
            .init()
            .with(this)
            .requestBuilder

        requestBuilder
            .load(urlPeta)
            .transition(
                DrawableTransitionOptions.withCrossFade()
            )
            .apply(
                RequestOptions()
                    .placeholder(
                        android.R.drawable.ic_menu_gallery
                    )
                    .error(
                        android.R.drawable.ic_lock_lock
                    )
                    .fitCenter()
            )
            .into(binding.imgItemPhoto)
    }
}