package kz.yshop.data.remote.responses.MainPageProducts

data class ProductX(
    val additional: Any,
    val article: String,
    val date_create: Int,
    val date_update: Int,
    val description_short_inline: String,
    val discount_price: String,
    val id: Int,
    val id_category: Any,
    val id_group1: Int,
    val id_group2: Int,
    val id_group3: Any,
    val id_shop: Int,
    val images: List<String>,
    val price: String,
    val quantity: Int,
    val title: String
)