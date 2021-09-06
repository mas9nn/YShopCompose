package kz.yshop.ui.details

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import kz.yshop.ui.main.MainScreenViewModel
import kz.yshop.util.Constants


@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun DetailsScreen(navHostController: NavHostController, viewModel: MainScreenViewModel) {
    var index by remember {
        mutableStateOf(0)
    }
    val product = viewModel.selectedProduct
    viewModel.scrollState.value = false
    viewModel.getProductDetail(product!!.id.toString())
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
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = product.title,
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
                    text = if (viewModel.productDetail.value != null) {
                        viewModel.productDetail.value!!.data.product.description!!
                    } else {
                        ""
                    },
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = if (viewModel.productDetail.value != null) {
                        viewModel.productDetail.value!!.data.product.description!!
                    } else {
                        ""
                    },
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = if (viewModel.productDetail.value != null) {
                        viewModel.productDetail.value!!.data.product.description!!
                    } else {
                        ""
                    },
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}