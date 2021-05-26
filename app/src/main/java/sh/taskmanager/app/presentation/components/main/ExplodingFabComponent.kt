package sh.taskmanager.app.presentation.components.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.taskmanager.app.R
import sh.taskmanager.app.presentation.theme.AccentColor
import sh.taskmanager.app.presentation.theme.SecondaryAccentColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExplodingFabComponent(onClick: () -> Unit) {

    val fabState = remember { mutableStateOf(FabState.EXPLODED) }

    val transition = updateTransition(
        targetState = if (fabState.value == FabState.EXPLODED) FabState.IDLE else FabState.EXPLODED,
        label = "explode"
    )

    val color by transition.animateColor(label = "color", transitionSpec = {
        tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
    }) { state ->
        when (state) {
            FabState.IDLE -> {
                AccentColor
            }
            FabState.EXPLODED -> {
                Color.White
            }
        }
    }

    val size by transition.animateDp(label = "size", transitionSpec = {
        keyframes {
            durationMillis = animationDuration
            58.dp at 0
            30.dp at 150
            58.dp at 250
            1000.dp at 400
            5000.dp at animationDuration
        }
    }) { state ->
        when (state) {
            FabState.IDLE -> {
                58.dp
            }
            FabState.EXPLODED -> {
                5000.dp
            }
        }
    }

    val alpha by transition.animateFloat(label = "size", transitionSpec = {
        tween(delayMillis = animationDuration, easing = FastOutSlowInEasing)
    }) { state ->
        when (state) {
            FabState.IDLE -> {
                1f
            }
            FabState.EXPLODED -> {
                0f
            }
        }
    }

    val elevation by transition.animateDp(label = "elevation", transitionSpec = {
        tween(delayMillis = animationDuration-200)
    }) { state ->
        when (state) {
            FabState.IDLE -> {
                0.dp
            }
            FabState.EXPLODED -> {
                0.dp
            }
        }
    }

    Box {
        FloatingActionButton(
            backgroundColor = color,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = elevation,
                pressedElevation = elevation
            ),
            modifier = Modifier
                .size(size = size).alpha(alpha),
            onClick = {
                fabState.value = FabState.IDLE
                GlobalScope.launch(Dispatchers.Main) {
                    delay(500)
                    onClick()
                }
            }) {
            Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
        }

    }
}

const val animationDuration = 700

enum class FabState {
    IDLE, EXPLODED
}