package kz.yshop.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import kz.yshop.ui.activity.MainActivityViewModel
import kz.yshop.util.Constants


@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun DetailsScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
) {
    var index by remember {
        mutableStateOf(0)
    }
    viewModel.scrollState.value = false
    val productDetails = detailsScreenViewModel.detailState.value

    if (viewModel.product != null && productDetails.productDetail != null) {
        val product = viewModel.product
        val coroutineScope = rememberCoroutineScope()
        val lazyRowScope = rememberLazyListState()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = rememberImagePainter(
                            data = Constants.BASE_URL + product!!.images[index],
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .height(350.dp)
                            .fillMaxWidth(0.7f)
                            .align(Alignment.CenterHorizontally)
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = lazyRowScope
                    ) {
                        itemsIndexed(product.images) { ind, image ->
                            Card(
                                modifier = Modifier
                                    .shadow(0.dp)
                                    .clickable {
                                        index = ind
                                        coroutineScope.launch {
                                            lazyRowScope.animateScrollToItem(
                                                if (ind != 0) {
                                                    ind - 1
                                                } else {
                                                    ind
                                                }
                                            )
                                        }
                                    }
                                    .widthIn(90.dp, 110.dp)
                                    .heightIn(76.dp, 96.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = Constants.BASE_URL + product.images[ind],
                                        builder = {
                                            crossfade(true)
                                        }
                                    ),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Color.White.copy(
                                                alpha = if (index == ind) {
                                                    0f
                                                } else {
                                                    0.4f
                                                }
                                            )
                                        )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                        Text(
                            text = product.title,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        if (productDetails.productDetail != null) {
                            FlowRow() {
                                productDetails.productDetail!!.data.path.forEach {
                                    Text(
                                        text = "${it.title}.",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Start,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = viewModel.secondColor.value
                                    )
                                ) {
                                    append("Article:")
                                }
                                append(product.article)
                            },
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Row() {
                            Text(
                                text = product.currency,
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box() {
                                Text(
                                    text = product.price,
                                    style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 24.sp,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                                if (product.discount_price != null && product.discount_price != 0f) {
                                    Box(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .background(Color.Gray)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            if (product.discount_price != null && product.discount_price != 0f) {
                                Text(
                                    text = product.discount_price.toString(),
                                    style = TextStyle(
                                        color = viewModel.secondColor.value,
                                        fontSize = 24.sp,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .clip(shape = RoundedCornerShape(30))
                                .background(color = viewModel.secondColor.value)
                        ) {
                            Text(
                                text = "Купить",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Описание",
                            style = TextStyle(
                                color = viewModel.secondColor.value,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = if (productDetails.productDetail != null) {
                                productDetails.productDetail!!.data.product.description!!
                            } else {
                                ""
                            },
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }
    }

}