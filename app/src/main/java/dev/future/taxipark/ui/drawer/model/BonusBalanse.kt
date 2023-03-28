package dev.future.taxipark.ui.drawer.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class BonusBalanse(

    @field:SerializedName("bonus_balans")
    val bonusBalans: Int? = null,

    @field:SerializedName("bonus_history")
    val bonusHistory: List<BonusHistoryItem?>? = null
) : Parcelable

@Parcelize
data class BonusHistoryItem(

    @field:SerializedName("total_summa")
    val totalSumma: String? = null,

    @field:SerializedName("total_bonus")
    val totalBonus: String? = null,

    @field:SerializedName("count")
    val count: String? = null,

    @field:SerializedName("day")
    val day: String? = null,

    @field:SerializedName("percent")
    val percent: Int? = null
) : Parcelable
