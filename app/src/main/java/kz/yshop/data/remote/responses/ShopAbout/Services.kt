package kz.yshop.data.remote.responses.ShopAbout

data class Services(
    val cloud_pk: Boolean,
    val delivery: Delivery,
    val is_pay_card: Int,
    val is_pay_cash: Int,
    val other_delivery: List<Any>,
    val selfpickup: List<Any>
)