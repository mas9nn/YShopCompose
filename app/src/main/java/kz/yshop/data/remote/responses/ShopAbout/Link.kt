package kz.yshop.data.remote.responses.ShopAbout

data class Link(
    val date_create: Int,
    val date_last_click: Any,
    val date_update: Int,
    val enable: Int,
    val id: Int,
    val id_shop: Int,
    val position: Int,
    val subtitle: Any,
    val title: String,
    val type: Int,
    val value: String
)