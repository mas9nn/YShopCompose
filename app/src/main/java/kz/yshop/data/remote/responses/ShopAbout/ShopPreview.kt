package kz.yshop.data.remote.responses.ShopAbout

data class ShopPreview(
    val currency_code: String,
    val customization: Customization,
    val description: String,
    val id: Int,
    val images: Images,
    val is_active: Int,
    val min_sum: Int,
    val services: Services,
    val subscribe_rules: SubscribeRules,
    val tag: String,
    val title: String
)