package kz.yshop.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kz.yshop.ui.components.Drawer
import kz.yshop.ui.main.MainScreenViewModel
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
                    Box(modifier = Modifier.fillMaxSize()) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Home"
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { openDrawer }) {
                                    Icon(Icons.Filled.Menu, contentDescription = "")
                                }
                            },
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        )
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
                            if (viewModel.shopInfo.value != null) {
                                Navigation(navController = navController, viewModel = viewModel)
                            }
                        }
                    }
                }
            }
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
