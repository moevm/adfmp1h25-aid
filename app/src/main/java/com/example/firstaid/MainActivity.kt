package com.example.firstaid

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firstaid.data.Datasource
import com.example.firstaid.ui.AboutScreen
import com.example.firstaid.ui.BookmarksScreen
import com.example.firstaid.ui.DisclaimerScreen
import com.example.firstaid.ui.LegalInformationScreen
import com.example.firstaid.ui.MainScreen
import com.example.firstaid.ui.theme.FirstAidTheme


class MainActivity : ComponentActivity() {
    companion object {
        lateinit var settingsSharedPreferences: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsSharedPreferences = getPreferences(MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAidApp()
        }
    }
}


sealed class NavBarItem(val route: Route, val label: String, val icon: ImageVector) {
    data object Main : NavBarItem(Route.Main, "Главная", Icons.Outlined.Home)
    data object Bookmarks : NavBarItem(Route.Bookmarks, "Закладки", Icons.Outlined.Star)
    data object About : NavBarItem(Route.About, "О приложении", Icons.Outlined.Info)
}

@Composable
fun FirstAidApp() {
    val navController = rememberNavController()
    val navigationItems = listOf(NavBarItem.Main, NavBarItem.Bookmarks, NavBarItem.About)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val routesWithoutBottomBar = listOf(Route.Disclaimer.name)

    FirstAidTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar = {
                when (currentDestination?.route) {
                    in routesWithoutBottomBar -> {}
                    else -> {
                        NavigationBar {
                            navigationItems.forEach { item ->
                                NavigationBarItem(
                                    selected = currentDestination?.route == item.route.name,
                                    icon = {
                                        Icon(item.icon, null)
                                    },
                                    label = { Text(item.label) },
                                    onClick = {
                                        navController.navigate(route = item.route.name) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )

                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            val showDisclaimer = MainActivity.settingsSharedPreferences.getBoolean(
                stringResource(R.string.show_disclaimer_prefs_name),
                true
            )

            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = if (showDisclaimer) Route.Disclaimer.name else Route.Main.name
            ) {

                composable(Route.Disclaimer.name) {
                    val prefsName = stringResource(R.string.show_disclaimer_prefs_name)

                    DisclaimerScreen(onAgreeClick = {
                        // Disable disclaimer on application startup
                        val editor = MainActivity.settingsSharedPreferences.edit()
                        editor.putBoolean(prefsName, false)
                        editor.apply()
                        navController.navigate(Route.Main.name)
                    })
                }

                composable(Route.Main.name) {
                    MainScreen(
                        onClickQuestionaireButton = {},
                        onClickGuidesButton = {},
                        onClickHospitalsButton = {},
                        onClickLegalInfoButton = { navController.navigate(Route.LegalInfo.name) }
                    )
                }

                composable(Route.Bookmarks.name) {
                    BookmarksScreen(
                        onBookmarkClick = {},
                        bookmarks = Datasource.guidesList.filter { it.inBookmarks }
                    )
                }

                composable(Route.About.name) { AboutScreen() }

                composable(Route.LegalInfo.name) {
                    LegalInformationScreen(
                        onBackClick = { navController.navigateUp() },
                        legalInfoList = Datasource.legalInfoList
                    )
                }
            }
        }
    }
}