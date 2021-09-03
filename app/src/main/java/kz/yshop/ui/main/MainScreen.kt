package kz.yshop.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kz.yshop.util.Constants.BASE_URL


@ExperimentalCoilApi
@Composable
fun MainScreen(viewModel: MainScreenViewModel) {

    val shop = viewModel.shopInfo

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 30.dp)) {
            Card(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally)
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
            Text(
                text = shop.value!!.data.shop_preview.title,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h2
            )
            Text(
                text = shop.value!!.data.shop_preview.description,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Center)
            )
        }
    }

}