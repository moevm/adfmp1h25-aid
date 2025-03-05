package com.example.firstaid

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firstaid.data.Datasource
import com.example.firstaid.ui.AboutScreen
import com.example.firstaid.ui.BookmarksScreen
import com.example.firstaid.ui.LegalInformationScreen
import com.example.firstaid.ui.MainScreen
import com.example.firstaid.ui.theme.FirstAidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAidApp()
        }
    }
}

sealed class Routes(val name: String) {
    data object Main : Routes("main")
    data object Bookmarks : Routes("bookmarks")
    data object About : Routes("about")
    data object LegalInfo : Routes("legalInfo")
}


sealed class NavBarItem(val route: Routes, val label: String, val icon: ImageVector) {
    data object Main : NavBarItem(Routes.Main, "Главная", Icons.Outlined.Home)
    data object Bookmarks : NavBarItem(Routes.Bookmarks, "Закладки", Icons.Outlined.Star)
    data object About : NavBarItem(Routes.About, "О приложении", Icons.Outlined.Info)
}

@Composable
@Preview
fun FirstAidApp() {
    val navController = rememberNavController()
    val navigationItems = listOf(NavBarItem.Main, NavBarItem.Bookmarks, NavBarItem.About)

    FirstAidTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar =
            {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
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
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = "main"
            ) {
                composable(Routes.Main.name) {
                    MainScreen(
                        onClickQuestionaireButton = {},
                        onClickGuidesButton = {},
                        onClickHospitalsButton = {},
                        onClickLegalInfoButton = { navController.navigate(Routes.LegalInfo.name) }
                    )
                }
                composable(Routes.Bookmarks.name) {
                    BookmarksScreen(
                        onBookmarkClick = {},
                        bookmarks = Datasource.guidesList.filter { it.inBookmarks }
                    )
                }
                composable(Routes.About.name) { AboutScreen() }
                composable(Routes.LegalInfo.name) {
                    LegalInformationScreen(
                        onBackClick = { navController.navigateUp() },
                        legalInfoList = Datasource.legalInfoList
                    )
                }
            }
        }
    }
}