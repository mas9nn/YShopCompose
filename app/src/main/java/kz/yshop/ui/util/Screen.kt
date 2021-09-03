package kz.yshop.ui.util

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object ShopScreen : Screen("shop_screen")
    object errorDialog :Screen("error_dialog")
}

