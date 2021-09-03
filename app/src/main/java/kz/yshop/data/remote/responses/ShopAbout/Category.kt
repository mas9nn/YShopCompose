package kz.yshop.data.remote.responses.ShopAbout

data class Category(
    val category: List<CategoryX>,
    val id: Int,
    val title: String
)