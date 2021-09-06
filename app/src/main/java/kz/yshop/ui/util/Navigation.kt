package kz.yshop.ui.util

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.compose.dialog
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import kz.yshop.ui.details.DetailsScreen
import kz.yshop.ui.dialogs.ErrorDialog
import kz.yshop.ui.main.MainScreen
import kz.yshop.ui.main.MainScreenViewModel

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainScreenViewModel
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {

        composable(Screen.MainScreen.route,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Red" ->
                        slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Red" ->
                        slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Red" ->
                        slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = { _, target ->
                when (target.destination.route) {
                    "Red" ->
                        slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(700))
                    else -> null
                }
            }
        ) {
            MainScreen(navController,viewModel)
        }
        composable(Screen.ProductDetails.route){
            DetailsScreen(navHostController = navController,viewModel = viewModel)
        }
        dialog(Screen.errorDialog.route) {
            ErrorDialog()
        }
    }
}