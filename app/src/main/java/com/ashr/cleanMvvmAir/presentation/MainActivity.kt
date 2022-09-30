package com.ashr.cleanMvvmAir.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ashr.cleanMvvmAir.presentation.city.data.CityDataScreen
import com.ashr.cleanMvvmAir.presentation.city.list.CityListScreen
import com.ashr.cleanMvvmAir.presentation.state.StateScreen
import com.ashr.cleanMvvmAir.presentation.ui.theme.Black200
import com.ashr.cleanMvvmAir.presentation.ui.theme.MyApplicationTheme
import com.ashr.cleanMvvmAir.presentation.ui.widget.Toolbar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()

        setContent {
            val themeState by viewModel.uiState.collectAsState()
            MyApplicationTheme(darkTheme = themeState.themIsDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                    content = {
                        val systemUiController = rememberSystemUiController()
                        val useDarkIcons = MaterialTheme.colors.isLight
                        SideEffect {
                            systemUiController.setSystemBarsColor(
                                color = if (useDarkIcons) Color.White else Black200,
                                darkIcons = useDarkIcons
                            )
                        }
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            MainScreen(viewModel)
                        }
                    })

            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val navController = rememberNavController()
    var canPop by remember { mutableStateOf(false) }
    val title = remember { mutableStateOf("") }
    navController.addOnDestinationChangedListener { controller, _, _ ->
        canPop = Screen.Country.route !== (controller.currentBackStackEntry?.destination?.route)

    }
    val navigationIcon: (@Composable () -> Unit) =
        if (canPop) {
            {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        } else {
            {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null,
                    Modifier.padding(4.dp),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    Scaffold(topBar = {
        Toolbar(
            navigationIcon = navigationIcon,
            title = title.value,
            onSwitchTheme = { viewModel.processIntents(MainUiIntent.ChangeTheme) }
        )
    }, content = { padding -> AppNavHost(navController = navController, title, padding) })
}


@Composable
fun AppNavHost(
    navController: NavHostController,
    title: MutableState<String>,
    padding: PaddingValues
) {
    NavHost(navController = navController, startDestination = Screen.Country.route) {
        composable(route = Screen.Country.route) {
            CountryScreen(
                title,
            ) {
                navController.navigate("state/${it}")
            }
        }
        composable(
            route = Screen.State.route,
            arguments = listOf(navArgument("countryName") { type = NavType.StringType })
        ) { backStackEntry ->
            StateScreen(backStackEntry.arguments?.getString("countryName"),
                title,
                onNavigateToCities = { countryName, stateName ->
                    navController.navigate("city/${countryName}/${stateName}")
                })
        }
        composable(
            route = Screen.City.route,
            arguments = listOf(
                navArgument("countryName") { type = NavType.StringType },
                navArgument("stateName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            CityListScreen(
                backStackEntry.arguments?.getString("countryName"),
                backStackEntry.arguments?.getString("stateName"),
                title,
                onNavigateToDetail = { countryName, stateName, cityName ->
                    navController.navigate("cityDetail/${countryName}/${stateName}/${cityName}")
                }
            )
        }
        composable(
            route = Screen.CityDetail.route,
            arguments = listOf(
                navArgument("countryName") { type = NavType.StringType },
                navArgument("stateName") { type = NavType.StringType },
                navArgument("cityName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            CityDataScreen(
                backStackEntry.arguments?.getString("countryName"),
                backStackEntry.arguments?.getString("stateName"),
                backStackEntry.arguments?.getString("cityName"),
                title
            )
        }
    }
}
