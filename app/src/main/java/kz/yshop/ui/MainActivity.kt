package kz.yshop.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.*
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
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kz.yshop.ui.components.Drawer
import kz.yshop.ui.main.*
import kz.yshop.ui.theme.YShopTheme
import kz.yshop.ui.util.Navigation
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: MainScreenViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @ExperimentalCoilApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                    val navController = rememberAnimatedNavController()
                    viewModel.error.observe(this, { action ->
                        Timber.wtf("observer $action")
                        com.google.android.material.snackbar.Snackbar.make(
                            window.decorView.rootView,
                            "Error",
                            com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
                        ).setAction("Retry") {
                            Timber.wtf("onClick")
                            action
                        }.show()
                    })
                    if (viewModel.openDrawer.value) {
                        scope.launch {
                            viewModel.openDrawer.value = false
                            drawerState.open()
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        ModalDrawer(
                            drawerState = drawerState,
                            gesturesEnabled = true,
                            drawerContent = {
                                Drawer(
                                    onDestinationClicked = { route ->
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    }
                                )
                            }
                        ) {
                            if (viewModel.shopInfo.value != null && viewModel.mainPage.value != null) {
                                Column() {
                                    CustomTopBar(viewModel)
                                    Navigation(navController = navController, viewModel = viewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @ExperimentalAnimationApi
    @Composable
    private fun CustomTopBar(viewModel: MainScreenViewModel) {
        var searchText by remember() {
            mutableStateOf("")
        }

        val accentColor =
            Color(android.graphics.Color.parseColor("#${viewModel.shopInfo.value!!.data.shop_preview.customization.accent_color}"))
        val secondColor =
            Color(android.graphics.Color.parseColor("#${viewModel.shopInfo.value!!.data.shop_preview.customization.second_color}"))
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
            AnimatedVisibility(visible = viewModel.scrollState.value) {
                ScrollableContentTopBar(this@MainActivity.viewModel)
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
                        .background(Color.White.copy(alpha = 0.2f)),
                    onClick = {
                        this@MainActivity.viewModel.openDrawer.value = true
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
                        .background(Color.White.copy(alpha = 0.2f)),
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
                                .align(Alignment.Start).fillMaxSize(),
                            value = searchText,
                            onValueChange = {
                                searchText = it
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            maxLines = 1,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            placeholder = {
                                Text(
                                    text = "Поиск По товарам", style = TextStyle(
                                        color = Color.White,
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
                        .background(Color.White.copy(alpha = 0.2f)),
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


//    @Composable
//    fun erroSnackBar(action: () -> Unit) {
//        Snackbar(
//            action = {
//                Button(onClick = action) {
//
//                }
//            }
//        ) {
//            Text(text = "Error")
//        }
//    }
}
