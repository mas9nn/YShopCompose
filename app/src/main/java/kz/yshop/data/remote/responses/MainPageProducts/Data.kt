package kz.yshop.data.remote.responses.MainPageProducts

data class Data(
    val all_products: AllProducts,
    val new_products: NewProducts,
    val popular_products: PopularProducts
)