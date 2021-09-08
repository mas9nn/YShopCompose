package kz.yshop.ui.details

import kz.yshop.data.remote.responses.MainPageProducts.Product
import kz.yshop.data.remote.responses.ProductDetail.ProductDetail

data class DetailsScreenState(

    var isLoading: Boolean = false,
    var productDetail: ProductDetail? = null,
    var product: Product? = null,
    val error: String = ""
)
