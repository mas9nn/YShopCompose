package kz.yshop.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
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

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
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
                    if (viewModel.shopInfo.value != null) {
                        Navigation(navController = navController, viewModel = viewModel)
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
