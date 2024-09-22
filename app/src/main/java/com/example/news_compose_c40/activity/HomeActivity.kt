//package com.example.news_compose_c40.activity
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.material3.DrawerState
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.toRoute
//import com.example.news_compose_c40.screens.categories.CategoriesRoute
//import com.example.news_compose_c40.screens.categories.CategoriesScreen
//import com.example.news_compose_c40.screens.news.NewsRoute
//import com.example.news_compose_c40.screens.news.NewsScreen
//import com.example.news_compose_c40.screens.news_details.NewsDetailsRoute
//import com.example.news_compose_c40.screens.news_details.NewsDetailsScreen
//import com.example.news_compose_c40.screens.search.SearchRoute
//import com.example.news_compose_c40.screens.search.SearchScreen
//import com.example.news_compose_c40.screens.settings.SettingsRoute
//import com.example.news_compose_c40.screens.settings.SettingsScreen
//import com.example.news_compose_c40.screens.settings.setAppLocale
//import com.example.news_compose_c40.ui.theme.NewsComposeC40Theme
//import com.example.news_compose_c40.widgets.NavigationDrawerSheet
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//
//@AndroidEntryPoint
//class HomeActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        actionBar?.hide()
//        installSplashScreen()
//        enableEdgeToEdge()
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            NewsComposeC40Theme {
//                NavigationDrawer()
//            }
//        }
//
//    }
//
//    fun openWebsiteForNews(url: String?) {
//        url?.let {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse(url)
//            startActivity(intent)
//        }
//    }
//}
//
//@Composable
//fun NavigationDrawer() {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    val navController = rememberNavController()
//
//    ModalNavigationDrawer(drawerContent = {
//        NavigationDrawerSheet(onNavigateToCategoriesClick = {
//            navController.popBackStack()
//
//            if (navController.currentDestination?.route != CategoriesRoute.toString()) {
//                navController.navigate(CategoriesRoute)
//            }
//            scope.launch {
//                drawerState.close()
//            }
//
//
//        }, onNavigateToSettingsClick = {
//            navController.navigate(SettingsRoute)
//            scope.launch {
//                drawerState.close()
//            }
//        })
//    }, drawerState = drawerState) {
//
//
//        NewsAppNavigation(navController, scope, drawerState)
//
//
//    }
//}
//
//
//@Composable
//fun NewsAppNavigation(
//    navController: NavHostController,
//    scope: CoroutineScope,
//    drawerState: DrawerState
//) {
//
//    NavHost(
//        navController = navController,
//        startDestination = CategoriesRoute
//    ) {
//        composable<CategoriesRoute> {
//
//            CategoriesScreen(
//                scope=scope,
//                drawerState=drawerState
//            ) { categoryApiID, categoryName ->
//                navController.navigate(NewsRoute(categoryApiID,categoryName))
//            }
//
//        }
//
//        composable<NewsRoute>{ navBackStackEntry ->
//            val route = navBackStackEntry.toRoute<NewsRoute>()
//            NewsScreen(
//                categoryID = route.categoryID,
//                categoryName =  route.categoryName,
//                scope = scope,
//                drawerState = drawerState,
//                onNewsClick =  { title,sourceName ->
//                    navController.navigate(NewsDetailsRoute(title,sourceName))
//
//                }, onSearchClick = {
//                    navController.navigate(SearchRoute)
//                })
//        }
//
//        composable<NewsDetailsRoute> {
//            val args = it.toRoute<NewsDetailsRoute>()
//            NewsDetailsScreen( sourceName = args.sourceName, scope = scope, drawerState = drawerState)
//        }
//
////        composable<SearchRoute> {
////            SearchScreen()
////        }
//
//        composable<SettingsRoute>{
//            SettingsScreen(scope = scope, drawerState = drawerState)
//        }
//    }
//
//
//}
//
//
//@Preview(showSystemUi = true)
//@Composable
//fun PreviewNewsScreen() {
//    NavigationDrawer()
//}
package com.example.news_compose_c40.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.news_compose_c40.screens.categories.CategoriesRoute
import com.example.news_compose_c40.screens.categories.CategoriesScreen
import com.example.news_compose_c40.screens.news.NewsRoute
import com.example.news_compose_c40.screens.news.NewsScreen
import com.example.news_compose_c40.screens.news_details.NewsDetailsRoute
import com.example.news_compose_c40.screens.news_details.NewsDetailsScreen
import com.example.news_compose_c40.screens.search.SearchRoute
import com.example.news_compose_c40.screens.search.SearchScreen
import com.example.news_compose_c40.screens.settings.SettingsRoute
import com.example.news_compose_c40.screens.settings.SettingsScreen
import com.example.news_compose_c40.screens.settings.getSavedLanguage
import com.example.news_compose_c40.screens.settings.setAppLocale
import com.example.news_compose_c40.ui.theme.NewsComposeC40Theme
import com.example.news_compose_c40.widgets.NavigationDrawerSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply the saved language when the activity starts
        applySavedLanguage()

        actionBar?.hide()
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            NewsComposeC40Theme {
                NavigationDrawer()
            }
        }

    }

    // Helper function to apply the saved language
    private fun applySavedLanguage() {
        val savedLanguage = getSavedLanguage(this) ?: "en" // Default to English
        setAppLocale(savedLanguage, this)
    }

    fun openWebsiteForNews(url: String?) {
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}

@Composable
fun NavigationDrawer() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

    ModalNavigationDrawer(drawerContent = {
        NavigationDrawerSheet(onNavigateToCategoriesClick = {
            navController.popBackStack()

            if (navController.currentDestination?.route != CategoriesRoute.toString()) {
                navController.navigate(CategoriesRoute)
            }
            scope.launch {
                drawerState.close()
            }


        }, onNavigateToSettingsClick = {
            navController.navigate(SettingsRoute)
            scope.launch {
                drawerState.close()
            }
        })
    }, drawerState = drawerState) {

        NewsAppNavigation(navController, scope, drawerState, context = LocalContext.current) // Pass context to handle locale
    }
}

@Composable
fun NewsAppNavigation(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    context: Context // Pass context for language handling
) {

    NavHost(
        navController = navController,
        startDestination = CategoriesRoute
    ) {
        composable<CategoriesRoute> {

            CategoriesScreen(
                scope = scope,
                drawerState = drawerState
            ) { categoryApiID, categoryName ->
                navController.navigate(NewsRoute(categoryApiID, categoryName))
            }

        }

        composable<NewsRoute> { navBackStackEntry ->
            val route = navBackStackEntry.toRoute<NewsRoute>()
            NewsScreen(
                categoryID = route.categoryID,
                categoryName = route.categoryName,
                scope = scope,
                drawerState = drawerState,
                onNewsClick = { title, sourceName ->
                    navController.navigate(NewsDetailsRoute(title, sourceName))
                },
                onSearchClick = {
                    navController.navigate(SearchRoute)
                })
        }

        composable<NewsDetailsRoute> {
            val args = it.toRoute<NewsDetailsRoute>()
            NewsDetailsScreen(sourceName = args.sourceName, scope = scope, drawerState = drawerState)
        }

        composable<SettingsRoute> {
            SettingsScreen(scope = scope, drawerState = drawerState, context = context) // Pass context to SettingsScreen
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewNewsScreen() {
    NavigationDrawer()
}
