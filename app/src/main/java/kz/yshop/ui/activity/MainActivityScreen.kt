package kz.yshop.ui.activity

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import kz.yshop.ui.components.CustomTopBar
import kz.yshop.ui.components.Drawer
import kz.yshop.ui.util.Navigation

@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MainActivityScreen(viewModel: MainActivityViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        val state = viewModel.user.value
        state.user?.let {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
            val navController = rememberNavController()
            if (viewModel.openDrawer.value) {
                scope.launch {
                    drawerState.open()
                }
            }
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
                    Box(modifier = Modifier.statusBarsPadding()) {
                        Drawer(
                            onDestinationClicked = { route ->
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                }
            ) {
                Column() {
                    CustomTopBar(viewModel)
                    Navigation(
                        navController = navController
                    )
                }
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}