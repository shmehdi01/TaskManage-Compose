package sh.taskmanager.app.presentation.components.main

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import sh.taskmanager.app.R
import sh.taskmanager.app.presentation.theme.PrimaryTextColor
import sh.taskmanager.app.presentation.theme.SecondaryTextColor


@OptIn(ExperimentalComposeApi::class)
@Composable
fun CategoryComponent() {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
        Text(
            stringResource(id = R.string.categories).toUpperCase(),
            style = TextStyle(
                color = SecondaryTextColor,
                fontSize = TextUnit(12f, TextUnitType.Sp),
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Row(Modifier.horizontalScroll(rememberScrollState())) {
            CategoryItem(name = "Business", progressValue = 0.5f)
            CategoryItem(
                name = "CodeChef", progressValue = 0.2f,
                color = colorResource(id = R.color.colorAccentSecondary),
                backgroundColor = colorResource(id = R.color.colorAccentSecondary40)
            )
            CategoryItem()
        }
    }
}


@OptIn(ExperimentalComposeApi::class)
@Composable
fun CategoryItem(
    name: String = "Category",
    progressValue: Float = 0.6f,
    color: Color = colorResource(id = R.color.colorAccent), backgroundColor: Color = colorResource(
        id = R.color.colorAccent40
    )
) {

    var progress by remember { mutableStateOf(0.0f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value


    LaunchedEffect(key1 = "progress", block = {
        delay(1000)
        progress = progressValue
    })

    Card(elevation = 2.dp, shape = RoundedCornerShape(12.dp), modifier = Modifier.padding(8.dp)) {
        Box(modifier = Modifier.padding(12.dp)) {
            Column(modifier = Modifier.padding(bottom = 12.dp)) {

                Text(
                    "40 task",
                    style = TextStyle(
                        color = SecondaryTextColor,
                        fontSize = TextUnit(12f, TextUnitType.Sp)
                    ),
                    modifier = Modifier.padding(top = 12.dp)
                )

                Text(
                    "Category",
                    style = TextStyle(
                        color = PrimaryTextColor, fontSize = TextUnit(16f, TextUnitType.Sp),
                        fontWeight = FontWeight.W500
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LinearProgressIndicator(
                    modifier = Modifier
                        .shadow(2.dp, shape = RoundedCornerShape(2.dp))
                        .animateContentSize(),
                    color = color,
                    backgroundColor = backgroundColor,
                    progress = animatedProgress
                )
            }
        }
    }
}