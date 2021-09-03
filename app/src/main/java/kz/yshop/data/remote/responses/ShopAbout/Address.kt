package kz.yshop.data.remote.responses.ShopAbout

data class Address(
    val address_text: String,
    val building_number: String,
    val city: String,
    val country: String,
    val is_can_visited: Int,
    val latitude: Double,
    val longitude: Double,
    val street: String
)