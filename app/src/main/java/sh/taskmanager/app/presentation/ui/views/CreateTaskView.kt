package sh.taskmanager.app.presentation.ui.views

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sh.taskmanager.app.presentation.theme.PrimaryColor
import sh.taskmanager.app.presentation.theme.PrimaryTextColor
import sh.taskmanager.app.presentation.theme.SecondaryTextColor


@OptIn(ExperimentalComposeApi::class)
@Composable
fun CreateTaskView(navController: NavController) {

    Scaffold(
        content = {
            Box(
                Modifier
                    .padding(vertical = 40.dp, horizontal = 20.dp)
                    .fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CardClose(navController)
                    }

                    TextField(value = "", onValueChange = {},
                        textStyle = TextStyle(
                            color = PrimaryTextColor,
                            fontSize = TextUnit(30f, TextUnitType.Sp),
                            fontWeight = FontWeight.W500
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Gray,
                            disabledTextColor = Color.Transparent,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(top = 100.dp),
                        placeholder = {
                            Text(
                                "Enter New Task", style = TextStyle(
                                    color = SecondaryTextColor,
                                    fontSize = TextUnit(30f, TextUnitType.Sp),
                                    fontWeight = FontWeight.W500
                                )
                            )
                        })

                }
            }
        },

        floatingActionButton = {
            AnimatedFloatingButton()
        }
    )
}


@Composable
fun AnimatedFloatingButton() {

    var shouldAnimate by remember { mutableStateOf(false) }

    val paddingValue = animateDpAsState(
        targetValue = if (shouldAnimate) 12.dp else 0.dp,
        animationSpec = tween(durationMillis = 600)
    ).value

    runBlocking {
        GlobalScope.launch {
            delay(100)
            shouldAnimate = true
        }
    }

    ExtendedFloatingActionButton(text = {
        Row(
            Modifier.padding(horizontal = paddingValue),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("New Task")
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
        }
    }, onClick = {
                 shouldAnimate = !shouldAnimate
    }, modifier = Modifier.height(55.dp))
}

@Composable
fun CardClose(navController: NavController) {
    Card(shape = CircleShape, modifier = Modifier.size(50.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = PrimaryTextColor,
                modifier = Modifier
            )
        }
    }
}