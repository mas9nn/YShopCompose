package kz.yshop.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import kz.yshop.data.remote.responses.MainPageProducts.Product
import kz.yshop.ui.activity.MainActivityViewModel
import kz.yshop.ui.util.Screen
import kz.yshop.util.Constants
import kotlin.math.ceil

@Composable
fun ProductItem(
    navController: NavHostController,
    item: Product,
    modifierColumn: Modifier = Modifier,
    modifierImage: Modifier = Modifier,
    secondColor: Color,
    viewModel: MainActivityViewModel
) {
    Column(modifier = modifierColumn) {
        Card(
            modifier = Modifier
                .shadow(0.dp)
                .clickable {
                    viewModel.product = item
                    navController.navigate(Screen.ProductDetails.route + "/${item.id}")
                }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = Constants.BASE_URL + item.images[0],
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
                append(item.currency)
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