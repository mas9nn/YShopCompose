package kz.yshop.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kz.yshop.R

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {

    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "App icon"
        )
//        screens.forEach { screen ->
//            Spacer(Modifier.height(24.dp))
//            Text(
//                text = screen.title,
//                style = MaterialTheme.typography.h4,
//                modifier = Modifier.clickable {
//                    onDestinationClicked(screen.route)
//                }
//            )
//        }
    }
}