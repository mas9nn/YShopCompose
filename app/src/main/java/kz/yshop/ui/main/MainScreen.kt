package kz.yshop.ui.main

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kz.yshop.R
import kz.yshop.data.remote.responses.MainPageProducts.Product
import kz.yshop.data.remote.responses.ShopAbout.Shop
import kz.yshop.ui.util.Screen
import kz.yshop.util.Constants.BASE_URL
import timber.log.Timber
import kotlin.math.ceil


private var shop: Shop? = null
private var accentColor = Color.Cyan
private var secondColor = Color.Cyan

@SuppressLint("StaticFieldLeak")
var controller: NavHostController? = null

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel
) {
    controller = navController
    shop = viewModel.shopInfo.value

    accentColor =
        Color(android.graphics.Color.parseColor("#${viewModel.shopInfo.value!!.data.shop_preview.customization.accent_color}"))
    secondColor =
        Color(android.graphics.Color.parseColor("#${viewModel.shopInfo.value!!.data.shop_preview.customization.second_color}"))

    Column(modifier = Modifier.background(Color.White)) {

        val modifier = Modifier
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            ListWithHeader(viewModel = viewModel, modifier)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListWithHeader(viewModel: MainScreenViewModel, modifier: Modifier) {
    val listState = rememberLazyListState()
    viewModel.scrollState.value = listState.firstVisibleItemIndex==0
    val data = viewModel.mainPage.value!!.data
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
                        ProductItemDouble(viewModel = viewModel, item)
                    }
                }
                1 -> {
                    stickyHeader {
                        Header(data.popular_products.title)
                    }
                    items(data.popular_products.products) { item ->
                        ProductItem(
                            viewModel,
                            item,
                            modifierColumn = Modifier.padding(14.dp),
                            modifierImage = Modifier.aspectRatio(0.75f)
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
                        ProductItemDouble(viewModel = viewModel, item)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItemDouble(viewModel: MainScreenViewModel, items: Pair<Product, Product>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        Arrangement.SpaceBetween
    ) {
        items.toList().forEachIndexed { index, item ->
            ProductItem(
                viewModel,
                item = item,
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
                modifierImage = Modifier.aspectRatio(0.85f)
            )
        }

    }
}

@Composable
fun ProductItem(
    viewModel: MainScreenViewModel,
    item: Product,
    modifierColumn: Modifier = Modifier,
    modifierImage: Modifier = Modifier
) {
    Column(modifier = modifierColumn) {
        Card(
            modifier = Modifier
                .shadow(0.dp)
                .clickable {
                    viewModel.selectedProduct = item
                    controller!!.navigate(Screen.ProductDetails.route)
                }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = BASE_URL + item.images[0],
                    builder = {
                        crossfade(true)
                    },
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = modifierImage
            )
            if (item.discount_price != null) {
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clip(shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                        .background(secondColor)
                ) {
                    Text(
                        text = "-${ceil((100 - (100 * item.discount_price / item.price.toDouble()))).toInt()}%",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                }
            }
        }
        Text(
            text = item.title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = item.description_short_inline,
            style = TextStyle(
                color = Color.Black,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            //text = shop!!.data.shop_preview.currency_code + " ${item.price}",
            text = buildAnnotatedString {
                append(shop!!.data.shop_preview.currency_code)
                append(" ")
                withStyle(
                    style = SpanStyle(
                        color = secondColor
                    )
                ) {
                    append(" ${item.price}")
                }
            },
            style = TextStyle(
                color = Color.Black,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun Header(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            text = title, style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ScrollableContentTopBar(viewModel: MainScreenViewModel) {
    val shop = viewModel.shopInfo
    Row(
        modifier = Modifier
            .padding(top = 14.dp, start = 14.dp, end = 14.dp)
            .fillMaxWidth(),
        Arrangement.SpaceBetween,
        Alignment.Top,
    ) {
        Row {
            Card(
                modifier = Modifier
                    .size(60.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(Color.White)
                    .align(Alignment.Top)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = BASE_URL + shop.value!!.data.shop_preview.images.logo,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = shop.value!!.data.shop_preview.title,
                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Icon(
            imageVector = Icons.Filled.Phone,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
