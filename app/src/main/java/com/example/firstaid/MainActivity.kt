package com.example.firstaid

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firstaid.data.Datasource
import com.example.firstaid.ui.AboutScreen
import com.example.firstaid.ui.BookmarksScreen
import com.example.firstaid.ui.DisclaimerScreen
import com.example.firstaid.ui.GuideDetailScreen
import com.example.firstaid.ui.GuidesListScreen
import com.example.firstaid.ui.HospitalDetailScreen
import com.example.firstaid.ui.HospitalsMapScreen
import com.example.firstaid.ui.LegalInformationScreen
import com.example.firstaid.ui.MainScreen
import com.example.firstaid.ui.QuestionnaireResultScreen
import com.example.firstaid.ui.QuestionnaireScreen
import com.example.firstaid.ui.SearchScreen
import com.example.firstaid.ui.theme.FirstAidTheme
import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    companion object {
        const val REQUEST_CALL_PHONE = 101
        lateinit var settingsSharedPreferences: SharedPreferences
        var pendingEmergencyNumber: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsSharedPreferences = getPreferences(MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAidApp()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            pendingEmergencyNumber?.let { number ->
                val intent = Intent(Intent.ACTION_CALL).apply {
                    data = Uri.parse(number)
                }
                startActivity(intent)
            }
        }
        pendingEmergencyNumber = null
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
    val routesWithoutBottomBar =
        listOf(Route.Disclaimer.name, Route.Search.name, Route.HospitalsMap.name)
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }

    fun handleEmergencyCall(number: String) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse(number)
            }
            context.startActivity(intent)
        } else {
            MainActivity.pendingEmergencyNumber = number
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.CALL_PHONE),
                MainActivity.REQUEST_CALL_PHONE
            )
        }
    }

    FirstAidTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (currentDestination?.route !in routesWithoutBottomBar) {
                    NavigationBar {
                        navigationItems.forEach { item ->
                            NavigationBarItem(
                                selected = currentDestination?.route == item.route.name,
                                icon = {
                                    Icon(item.icon, contentDescription = null)
                                },
                                label = { Text(item.label) },
                                onClick = {
                                    navController.navigate(route = item.route.name) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = false
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

                composable(Route.GuidesList.name) {
                    GuidesListScreen(
                        guides = Datasource.guidesList,
                        onGuideClick = { guideId ->
                            navController.navigate("${Route.GuideDetail.name}/$guideId")
                        },
                        onBackClick = { navController.navigateUp() },
                        onClickSearchBar = { navController.navigate(Route.Search.name) }
                    )
                }

                composable(Route.GuideDetail.name + "/{GUIDE_ID}") { backStackEntry ->
                    val context = LocalContext.current
                    val guideId =
                        backStackEntry.arguments?.getString("GUIDE_ID")?.toIntOrNull() ?: -1
                    val guide = Datasource.guidesList.find { it.id == guideId } ?: return@composable

                    GuideDetailScreen(
                        context = context,
                        guide = guide,
                        onBackClick = { navController.navigateUp() },
                        onBookmarkUpdate = { /* Update bookmarks if necessary */ },
                        onShareClick = { /* Share functionality */ }
                    )
                }

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
                        onClickQuestionaireButton = { navController.navigate(Route.Questionnaire.name) },
                        onClickGuidesButton = { navController.navigate(Route.GuidesList.name) },
                        onClickHospitalsButton = { navController.navigate(Route.HospitalsMap.name) },
                        onClickSearchBar = { navController.navigate(Route.Search.name) },
                        onClickLegalInfoButton = { navController.navigate(Route.LegalInfo.name)},
                        onEmergencyCall = { number ->
                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    showPermissionDialog = true
                                    MainActivity.pendingEmergencyNumber = number
                                } else {
                                    val intent = Intent(Intent.ACTION_CALL).apply {
                                        data = Uri.parse(number)
                                    }
                                    context.startActivity(intent)
                                }
                            }
                    )
                }

                composable(Route.Bookmarks.name) {
                    BookmarksScreen(
                        onBookmarkClick = { guideId ->
                            navController.navigate("${Route.GuideDetail.name}/$guideId")
                        },
                        onBackClick = { navController.navigateUp() }
                    )
                }

                composable(Route.About.name) { AboutScreen() }

                composable(Route.LegalInfo.name) {
                    LegalInformationScreen(
                        onBackClick = { navController.navigateUp() },
                        legalInfoList = Datasource.legalInfoList
                    )
                }
                composable(Route.Search.name) {
                    SearchScreen(
                        onInstitutionClick = { hospitalId ->
                            navController.navigate("${Route.HospitalDetail.name}/$hospitalId")
                        },
                        onBackClick = { navController.navigateUp() },
                        guides = Datasource.guidesList,
                        hospitals = Datasource.hospitalsList, // Add hospitals parameter
                        onGuideClick = { guideId ->
                            navController.navigate("${Route.GuideDetail.name}/$guideId")
                        }
                    )
                }
                composable(
                    Route.HospitalsMap.name + "?hospitalId={HOSPITAL_ID}",
                    arguments = listOf(navArgument("HOSPITAL_ID") {
                        type = NavType.IntType
                        defaultValue = -1
                    })
                ) { navBackStackEntry ->
                    val hospitalId = navBackStackEntry.arguments?.getInt("HOSPITAL_ID") ?: -1
                    HospitalsMapScreen(
                        hospitalId = hospitalId,
                        onBackClick = { navController.navigateUp() },
                        onClickSearchBar = { navController.navigate(Route.Search.name) }
                    )
                }

                composable(Route.Questionnaire.name) {
                    QuestionnaireScreen(
                        questions = Datasource.questions,
                        guides = Datasource.guidesList,
                        onFinish = { matchingGuides ->
                            navController.navigate(
                                "${Route.QuestionnaireResult.name}/${
                                    matchingGuides.joinToString(
                                        ","
                                    ) { it.id.toString() }
                                }"
                            )
                        }
                    )
                }

                composable("${Route.QuestionnaireResult.name}/{GUIDE_IDS}") { backStackEntry ->
                    val guideIds = backStackEntry.arguments?.getString("GUIDE_IDS")
                        ?.split(",")
                        ?.mapNotNull { it.toIntOrNull() } ?: emptyList()
                    val matchingGuides = Datasource.guidesList.filter { it.id in guideIds }

                    QuestionnaireResultScreen(
                        matchingGuides = matchingGuides,
                        onBackClick = {
                            navController.popBackStack(Route.Main.name, inclusive = false)
                        },
                        onGuideClick = { guideId ->
                            navController.navigate("${Route.GuideDetail.name}/$guideId")
                        }
                    )
                }

                composable("${Route.HospitalDetail.name}/{HOSPITAL_ID}") { backStackEntry ->
                    val hospitalId =
                        backStackEntry.arguments?.getString("HOSPITAL_ID")?.toIntOrNull() ?: -1
                    val hospital = Datasource.hospitalsList.firstOrNull { it.id == hospitalId }
                        ?: return@composable

                    HospitalDetailScreen(
                        hospital = hospital,
                        onBackClick = { navController.navigateUp() },
                        onToHospitalMapClick = { navController.navigate(Route.HospitalsMap.name + "?hospitalId=${hospital.id}") }
                    )
                }
            }
            if (showPermissionDialog) {
                AlertDialog(
                    onDismissRequest = { showPermissionDialog = false },
                    title = { Text("Разрешение требуется") },
                    text = { Text("Для совершения вызова необходимо разрешение") },
                    confirmButton = {
                        Button(onClick = {
                            ActivityCompat.requestPermissions(
                                context as Activity,
                                arrayOf(Manifest.permission.CALL_PHONE),
                                MainActivity.REQUEST_CALL_PHONE
                            )
                            showPermissionDialog = false
                        }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}