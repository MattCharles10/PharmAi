package com.pharmai.core



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pharmai.presentation.screens.*

object Navigation {
    sealed class Screen(val route: String) {
        object Home : Screen("home")
        object Search : Screen("search")
        object Camera : Screen("camera")
        object Inventory : Screen("inventory")
        object Profile : Screen("profile")
        object DrugDetail : Screen("drug_detail/{drugId}") { fun createRoute(id: String) = "drug_detail/$id" }
        object Reminders : Screen("reminders")
        object ScanResult : Screen("scan_result/{scanId}") { fun createRoute(id: String) = "scan_result/$id" }
    }

    sealed class BottomNavItem(val route: String, val title: String, val selected: ImageVector, val unselected: ImageVector) {
        object Home : BottomNavItem(Screen.Home.route, "Home", Icons.Filled.Home, Icons.Outlined.Home)
        object Search : BottomNavItem(Screen.Search.route, "Search", Icons.Filled.Search, Icons.Outlined.Search)
        object Camera : BottomNavItem(Screen.Camera.route, "Scan", Icons.Filled.CameraAlt, Icons.Outlined.CameraAlt)
        object Inventory : BottomNavItem(Screen.Inventory.route, "Inventory", Icons.Filled.Inventory, Icons.Outlined.Inventory)
        object Profile : BottomNavItem(Screen.Profile.route, "Profile", Icons.Filled.Person, Icons.Outlined.Person)
    }

    val bottomNavItems = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Camera, BottomNavItem.Inventory, BottomNavItem.Profile)

    @Composable
    fun NavGraph(navController: NavHostController = rememberNavController(), startDest: String = Screen.Home.route) {
        NavHost(navController, startDest) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Search.route) { SearchScreen(navController) }
            composable(Screen.Camera.route) { CameraScreen(navController) }
            composable(Screen.Inventory.route) { InventoryScreen(navController) }
            composable(Screen.Profile.route) { ProfileScreen(navController) }
            composable(Screen.Reminders.route) { ReminderScreen(navController) }
            composable(Screen.DrugDetail.route, arguments = listOf(navArgument("drugId") { type = NavType.StringType })) {
                DrugDetailScreen(navController, it.arguments?.getString("drugId") ?: "")
            }
            composable(Screen.ScanResult.route, arguments = listOf(navArgument("scanId") { type = NavType.StringType })) {
                ScanResultScreen(navController, it.arguments?.getString("scanId") ?: "")
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        NavigationBar {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            bottomNavItems.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(if (currentRoute == item.route) item.selected else item.unselected, item.title) },
                    label = { Text(item.title) },
                    selected = currentRoute == item.route,
                    onClick = { navController.navigate(item.route) { popUpTo(Screen.Home.route); launchSingleTop = true } }
                )
            }
        }
    }
}