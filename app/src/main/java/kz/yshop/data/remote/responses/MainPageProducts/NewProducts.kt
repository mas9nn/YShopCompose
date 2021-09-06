package kz.yshop.data.remote.responses.MainPageProducts

data class NewProducts(
    val `class`: String,
    val products: List<Product>,
    val title: String
)