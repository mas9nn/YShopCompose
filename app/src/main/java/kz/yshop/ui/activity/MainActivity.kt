package kz.yshop.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kz.yshop.data.remote.responses.MainPageProducts.Product
import kz.yshop.ui.theme.YShopTheme
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @ExperimentalPagerApi
    @ExperimentalCoilApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            YShopTheme {
                // A surface container using the 'background' color from the theme
                ProvideWindowInsets() {
                    Surface(color = MaterialTheme.colors.background) {
                        MainActivityScreen()
                    }
                }
            }
        }
    }

}
