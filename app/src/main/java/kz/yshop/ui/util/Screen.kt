package kz.yshop.ui.util

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object ShopScreen : Screen("shop_screen")
    object ProductDetails : Screen("product_details/{product}")
    object errorDialog :Screen("error_dialog")
}

