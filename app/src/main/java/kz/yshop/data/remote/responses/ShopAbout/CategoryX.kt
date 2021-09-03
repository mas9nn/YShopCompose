package kz.yshop.data.remote.responses.ShopAbout

data class CategoryX(
    val id: Int,
    val subcategory: List<Subcategory>,
    val title: String
)