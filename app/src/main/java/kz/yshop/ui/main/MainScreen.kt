package kz.yshop.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import kz.yshop.data.remote.responses.MainPageProducts.Product
import kz.yshop.ui.activity.MainActivityViewModel
import kz.yshop.ui.main.components.Header
import kz.yshop.ui.main.components.ProductItem
import timber.log.Timber


private var accentColor = Color.Cyan
private var secondColor = Color.Cyan

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    val shopInfo = mainScreenViewModel.shopInfo.value

    Column(modifier = Modifier.background(Color.White)) {

        val modifier = Modifier
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            if (shopInfo.shop != null) {
                accentColor =
                    Color(android.graphics.Color.parseColor("#${shopInfo.shop!!.data.shop_preview.customization.accent_color}"))
                secondColor =
                    Color(android.graphics.Color.parseColor("#${shopInfo.shop!!.data.shop_preview.customization.second_color}"))
                viewModel.accentColor.value = accentColor
                viewModel.secondColor.value = secondColor
                ListWithHeader(
                    navController = navController,
                    viewModel = viewModel,
                    modifier = modifier
                )
            }
            if (shopInfo.error.isNotBlank()) {
                Text(
                    text = shopInfo.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (shopInfo.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListWithHeader(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    modifier: Modifier,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()

    viewModel.scrollState.value = listState.firstVisibleItemIndex == 0
    if (mainScreenViewModel.shopInfo.value.products != null) {
        val data = mainScreenViewModel.shopInfo.value.products!!.data
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state = listState
        ) {
            listOf("", "", "").forEachIndexed { index, item ->
                when (index) {
                    0 -> {
                        stickyHeader {
                            Header(data.new_products.title)
                        }
                        Timber.wtf(
                            data.new_products.products.size.toString()
                        )
                        items(
                            data.new_products.products.windowed(2, 2)
                                .map { Pair(it[0], it[1]) }) { item ->
                            ProductItemDouble(
                                navController = navController,
                                items = item,
                                mainActivityViewModel = viewModel
                            )
                        }
                    }
                    1 -> {
                        stickyHeader {
                            Header(data.popular_products.title)
                        }
                        items(data.popular_products.products) { item ->
                            ProductItem(
                                navController = navController,
                                item.apply {
                                    currency =
                                        mainScreenViewModel.shopInfo.value.shop!!.data.shop_preview.currency_code
                                },
                                modifierColumn = Modifier.padding(14.dp),
                                modifierImage = Modifier.aspectRatio(0.75f),
                                secondColor = secondColor,
                                viewModel = viewModel
                            )
                        }
                    }
                    2 -> {
                        stickyHeader {
                            Header(data.all_products.title)
                        }
                        Timber.wtf(
                            data.all_products.products.windowed(2, 2)
                                .map { Pair(it[0], it[1]) }.size.toString()
                        )
                        items(
                            data.all_products.products.windowed(2, 2)
                                .map { Pair(it[0], it[1]) }) { item ->
                            ProductItemDouble(navController = navController, item, viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItemDouble(
    navController: NavHostController,
    items: Pair<Product, Product>,
    mainActivityViewModel: MainActivityViewModel,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        Arrangement.SpaceBetween
    ) {
        items.toList().forEachIndexed { index, item ->
            ProductItem(
                navController = navController,
                item = item.apply {
                    currency =
                        mainScreenViewModel.shopInfo.value.shop!!.data.shop_preview.currency_code
                },
                modifierColumn = Modifier
                    .weight(0.5f)
                    .padding(
                        start = if (index == 0) {
                            14.dp
                        } else {
                            7.dp
                        },
                        top = 14.dp,
                        end = if (index == 0) {
                            7.dp
                        } else {
                            14.dp
                        },
                        bottom = 14.dp
                    ),
                modifierImage = Modifier.aspectRatio(0.85f),
                secondColor = secondColor,
                viewModel = mainActivityViewModel
            )
        }

    }
}






