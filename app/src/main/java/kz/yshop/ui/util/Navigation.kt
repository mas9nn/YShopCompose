package kz.yshop.ui.util

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.dialog
import coil.annotation.ExperimentalCoilApi

import com.google.accompanist.pager.ExperimentalPagerApi
import kz.yshop.ui.activity.MainActivityViewModel
import kz.yshop.ui.details.DetailsScreen
import kz.yshop.ui.dialogs.ErrorDialog
import kz.yshop.ui.main.MainScreen


@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainActivityViewModel = hiltViewModel()
) {

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {

        composable(Screen.MainScreen.route){
            MainScreen(navController, viewModel)
        }
        composable(Screen.ProductDetails.route + "/{productId}") {
            DetailsScreen(navHostController = navController, viewModel = viewModel)
        }
        dialog(Screen.errorDialog.route) {
            ErrorDialog()
        }
    }
}