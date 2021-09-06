package kz.yshop.data.remote.responses.MainPageProducts

data class AllProducts(
    val `class`: String,
    val products: List<Product>,
    val title: String
)