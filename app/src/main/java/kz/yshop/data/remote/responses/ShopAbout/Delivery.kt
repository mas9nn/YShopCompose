package kz.yshop.data.remote.responses.ShopAbout

data class Delivery(
    val country: Boolean,
    val description: Any,
    val free_after: Int,
    val mid_time: Any,
    val min_sum: Int,
    val price: Int
)