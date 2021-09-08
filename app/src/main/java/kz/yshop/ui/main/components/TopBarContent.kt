package kz.yshop.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import kz.yshop.ui.main.MainScreenViewModel
import kz.yshop.util.Constants

@Composable
fun ScrollableContentTopBar(viewModel: MainScreenViewModel = hiltViewModel()) {
    val shop = viewModel.shopInfo.value.shop
    if(shop!=null){
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
                            data = Constants.BASE_URL + shop.data.shop_preview.images.logo,
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
                    text = shop.data.shop_preview.title,
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
}