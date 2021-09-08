package kz.yshop.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kz.yshop.ui.activity.MainActivityViewModel

import kz.yshop.ui.main.components.ScrollableContentTopBar

@ExperimentalAnimationApi
@Composable
fun CustomTopBar(viewModel: MainActivityViewModel = hiltViewModel()) {
    val systemUiController = rememberSystemUiController()
    val insets = LocalWindowInsets.current
    var searchText by remember() {
        mutableStateOf("")
    }

    val accentColor = viewModel.accentColor.value
    val secondColor = viewModel.secondColor.value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        accentColor,
                        secondColor
                    )
                )
            ),
        Arrangement.Top
    ) {
        Spacer(modifier = Modifier.statusBarsHeight())
        AnimatedVisibility(visible = viewModel.scrollState.value) {
            ScrollableContentTopBar()
        }
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 14.dp), Arrangement.SpaceEvenly
        ) {
            IconButton(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(androidx.compose.ui.graphics.Color.White.copy(alpha = 0.2f)),
                onClick = {
                    viewModel.openDrawer.value = true
                }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Button Menu",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(androidx.compose.ui.graphics.Color.White.copy(alpha = 0.2f)),
                Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Button Search",
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                        .align(Alignment.CenterVertically)
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxSize()
                ) {
                    TextField(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .fillMaxSize(),
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = androidx.compose.ui.graphics.Color.Transparent,
                            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                            disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                        ),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = TextStyle(
                            color = androidx.compose.ui.graphics.Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        placeholder = {
                            Text(
                                text = "Поиск По товарам", style = TextStyle(
                                    color = androidx.compose.ui.graphics.Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(androidx.compose.ui.graphics.Color.White.copy(alpha = 0.2f)),
                onClick = {
                }) {
                Icon(
                    imageVector = Icons.Filled.AddShoppingCart,
                    contentDescription = "Button Cart",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
    }
}