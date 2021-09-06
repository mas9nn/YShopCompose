package kz.yshop.data.remote.responses.MainPageProducts

data class PopularProducts(
    val `class`: String,
    val products: List<Product>,
    val title: String
)