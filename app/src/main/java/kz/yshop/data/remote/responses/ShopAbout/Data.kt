package kz.yshop.data.remote.responses.ShopAbout

data class Data(
    val address: Address,
    val category: List<Category>,
    val links: List<Link>,
    val mode: List<Mode>,
    val phones: Phones,
    val shop_preview: ShopPreview
)