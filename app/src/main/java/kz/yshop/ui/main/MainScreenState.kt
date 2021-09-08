package kz.yshop.ui.main

import kz.yshop.data.remote.responses.MainPageProducts.MainPageProducts
import kz.yshop.data.remote.responses.ShopAbout.Shop

class MainScreenState(
    var isLoading:Boolean = false,
    var shop: Shop? = null,
    var products : MainPageProducts? = null,
    val error:String = ""
) {

}