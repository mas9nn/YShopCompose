package kz.yshop.data.remote.responses.MainPageProducts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val additional: String?,
    val article: String,
    val date_create: Int,
    val date_update: Int,
    val description_short_inline: String,
    val discount_price: Float?,
    val id: Int,
    val id_category: Int?,
    val id_group1: Int,
    val id_group2: Int?,
    val id_group3: Int?,
    val id_shop: Int,
    val images: List<String>,
    val price: String,
    val quantity: Int,
    val title: String
): Parcelable