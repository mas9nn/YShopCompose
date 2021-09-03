package kz.yshop.ui.util

sealed class Error(val responseName:String){
    object ShopAbout:Error("shop_about")
    object UserDemo:Error("user_demo")
}
