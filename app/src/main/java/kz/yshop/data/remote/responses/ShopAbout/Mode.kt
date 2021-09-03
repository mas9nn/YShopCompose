package kz.yshop.data.remote.responses.ShopAbout

data class Mode(
    val day: Int,
    val day_short: String,
    val status: Int,
    val time_close: String,
    val time_open: String,
    val today: Int
)