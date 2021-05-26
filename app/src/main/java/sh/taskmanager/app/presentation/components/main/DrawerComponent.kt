package sh.taskmanager.app.presentation.components.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import sh.taskmanager.app.R
import sh.taskmanager.app.presentation.theme.SecondaryTextColor

@OptIn(ExperimentalComposeApi::class)
@Composable
fun DrawerComponent(modifier: Modifier = Modifier, onBackPress: () -> Unit) {

    Surface(color = colorResource(id = R.color.colorSecondary), modifier = Modifier.fillMaxSize()) {
        Box(modifier.padding(top = 60.dp)) {
            Column {
                Header(onBackPress)
                Name()
                Menu()
            }
        }
    }
}


@Composable
private fun Header(onBackPress: () -> Unit) {
    Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
        Row {
            Box(
                modifier = Modifier.padding(end = 60.dp, top = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(60.dp)
                        .background(Color.White)
                )

                // Determinate
                CircularProgressIndicator(
                    progress = 1f, Modifier.size(78.dp),
                    color = SecondaryTextColor, strokeWidth = 2.dp
                )

                // Determinate
                CircularProgressIndicator(
                    progress = 0.3f, Modifier.size(80.dp),
                    color = colorResource(id = R.color.colorAccentSecondary),
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(width = 1.dp, shape = CircleShape, color = Color.LightGray)
                    .size(40.dp)
                    .clickable(onClick = onBackPress)
            ) {

                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeApi::class)
@Composable
private fun Name(name: String = "Joy\nMitchel") {
    Text(
        name,
        Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
        style = TextStyle(
            color = Color.White,
            fontSize = TextUnit(35f, TextUnitType.Sp),
            fontWeight = FontWeight.W500
        )
    )
}


@OptIn(ExperimentalComposeApi::class)
@Composable
private fun Menu(modifier: Modifier = Modifier.padding(horizontal = 20.dp)) {

    Box(modifier = modifier) {
        Column {
            MenuItem(icon = Icons.Default.List, label = "Templates")
            MenuItem(icon = Icons.Default.ArrowDropDown, label = "Categories")
            MenuItem(icon = Icons.Default.DateRange, label = "Analytics")
            MenuItem(icon = Icons.Default.Settings, label = "Settings")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeApi::class)
@Composable
private fun MenuItem(icon: ImageVector, label: String) {
    ListItem(
        icon = {
            Icon(imageVector = icon, contentDescription = null, tint = Color.White)
        }, text = {
            Text(label, style = TextStyle(
                color = Color.White,
                fontSize = TextUnit(14f, TextUnitType.Sp),
                fontWeight = FontWeight.W500))
        })
}


