package id.ac.polbeng.akshaandestian.kabkotaretrofitapp.response

import com.google.gson.annotations.SerializedName
import id.ac.polbeng.akshaandestian.kabkotaretrofitapp.model.KabKotaItem

data class KabKotaResponse(
	@field:SerializedName("data")
	val data: List<KabKotaItem>,

	@field:SerializedName("message")
	val message: String
)