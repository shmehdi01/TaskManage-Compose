package sh.taskmanager.app.presentation.components.main

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.taskmanager.app.R
import sh.taskmanager.app.presentation.theme.AccentColor
import sh.taskmanager.app.presentation.theme.SecondaryAccentColor
import sh.taskmanager.app.presentation.theme.SecondaryTextColor
import sh.taskmanager.app.presentation.theme.Teal200

@OptIn(ExperimentalComposeApi::class)
@Composable
fun TodayTaskComponent() {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
        Text(
            stringResource(id = R.string.today_task).toUpperCase(),
            style = TextStyle(
                color = SecondaryTextColor,
                fontSize = TextUnit(12f, TextUnitType.Sp),
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {
            TaskItem(taskName = "Daily Meeting With Team")
            TaskItem(taskName = "Pay for rent", isCompleted = true)
            TaskItem(taskName = "Check mails", color = SecondaryAccentColor)
        }
    }
}

@Composable
fun TaskItem(taskName: String = "Task", color: Color = AccentColor, isCompleted: Boolean = false) {

    val currentState = remember {
        mutableStateOf(if (isCompleted) TaskAnimationDef.TaskItemState.COMPLETED else TaskAnimationDef.TaskItemState.ACTIVE)
    }

    val transition = updateTransition(currentState, label = "pump")

    val isTaskComplete = currentState.value == TaskAnimationDef.TaskItemState.COMPLETED

    val sizeDp = TaskAnimationDef.DPAnimation(transition)
    val colorChange = TaskAnimationDef.ColorAnimation(transition, color)

    Card(Modifier.padding(4.dp), elevation = 0.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = sizeDp, horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            Checkbox(
                checked = isTaskComplete, //completedState.value,
                onCheckedChange = {
                    if (currentState.value == TaskAnimationDef.TaskItemState.ACTIVE) {
                        currentState.value = TaskAnimationDef.TaskItemState.SUBMIT
                        GlobalScope.launch {
                            delay(300)
                            currentState.value = TaskAnimationDef.TaskItemState.COMPLETED
                            //completedState.value = true
                        }

                    } else
                        currentState.value = TaskAnimationDef.TaskItemState.ACTIVE
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorChange.value,
                    uncheckedColor = colorChange.value,
                    disabledColor = colorChange.value
                ),
                enabled = !isTaskComplete,
                modifier = Modifier
            )


            Text(
                taskName,
                textDecoration = if (isTaskComplete) TextDecoration.LineThrough else null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}


object TaskAnimationDef {

    enum class TaskItemState {
        ACTIVE, SUBMIT, COMPLETED
    }

    @Composable
    fun ColorAnimation(
        transition: Transition<MutableState<TaskItemState>>,
        color: Color,
    ): State<Color> {

        return transition.animateColor(label = "color") { state ->
            when (state.value) {
                TaskItemState.ACTIVE -> {
                    color
                }
                TaskItemState.SUBMIT -> {
                    Teal200
                }
                TaskItemState.COMPLETED -> {
                    SecondaryTextColor
                }
            }
        }
    }


    @Composable
    fun DPAnimation(transition: Transition<MutableState<TaskItemState>>): Dp {
        val pump by transition.animateDp(label = "pump") { state ->

            when (state.value) {
                TaskItemState.ACTIVE -> {
                    18.dp
                }
                TaskItemState.SUBMIT -> {
                    50.dp
                }
                TaskItemState.COMPLETED -> {
                    18.dp
                }
            }
        }

        return pump
    }

}

