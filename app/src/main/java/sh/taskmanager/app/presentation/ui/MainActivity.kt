package sh.taskmanager.app.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import sh.taskmanager.app.R
import sh.taskmanager.app.presentation.components.main.CategoryComponent
import sh.taskmanager.app.presentation.components.main.DrawerComponent
import sh.taskmanager.app.presentation.components.main.ExplodingFabComponent
import sh.taskmanager.app.presentation.components.main.TodayTaskComponent
import sh.taskmanager.app.presentation.theme.PrimaryTextColor
import sh.taskmanager.app.presentation.theme.TaskManagerTheme
import sh.taskmanager.app.presentation.ui.views.CreateTaskView

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            TaskManagerTheme(darkTheme = false) {
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainContent(navController = navController) }
                    composable("create-task") { CreateTaskView(navController = navController) }

                }
            }
        }
    }
}


@Composable
fun HomeAppBar(onNavigationClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                )
            }
        },

        title = {

        },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.padding(horizontal = 12.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        },
        backgroundColor = colorResource(id = R.color.colorBackground),
        modifier = Modifier.padding(horizontal = 12.dp),
        elevation = 0.dp
    )
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(navController: NavController) {

    var showDrawer by remember { mutableStateOf(false) }

    val offsetX: Dp by animateDpAsState(
        targetValue = if (showDrawer) 200.dp else 0.dp,
        animationSpec = tween(durationMillis = 700)
    )
    val padding: Dp by animateDpAsState(
        targetValue = if (showDrawer) 80.dp else 0.dp,
        animationSpec = tween(durationMillis = 400)
    )
    val corner: Dp by animateDpAsState(
        targetValue = if (showDrawer) 20.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000)
    )

    val scaffoldModifier = Modifier
        .offset(x = offsetX)
        .padding(top = padding, bottom = padding, start = padding)
        .clip(RoundedCornerShape(corner))

    Box(modifier = Modifier) {
        DrawerComponent(onBackPress = {
            showDrawer = !showDrawer
        })
        Scaffold(
            backgroundColor = colorResource(id = R.color.colorBackground),
            topBar = {
                HomeAppBar(
                    onNavigationClick = {
                        showDrawer = !showDrawer
                    }
                )
            },
            content = {
                HomeContent()
            },
            floatingActionButton = {
                ExplodingFabComponent(onClick = {
                    navController.navigate("create-task")
                })

            },
            modifier = scaffoldModifier
        )
    }
}


@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        NameComponent()
        CategoryComponent()
        TodayTaskComponent()
    }
}


@OptIn(ExperimentalComposeApi::class)
@Composable
fun NameComponent(name: String = "Joy") {

    val welcomeText = "${stringResource(id = R.string.welcome_prefix)}, $name!"

    Box(modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp)) {
        Text(
            welcomeText, style = TextStyle(
                PrimaryTextColor,
                fontSize = TextUnit(42f, TextUnitType.Sp)
            ),
            fontWeight = FontWeight.Bold
        )
    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    TaskItem()
//}