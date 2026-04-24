package com.pharmai.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pharmai.features.home.presentation.HomeScreen
import com.pharmai.features.search.presentation.SearchScreen
import com.pharmai.features.scanner.presentation.CameraScreen
import com.pharmai.features.inventory.presentation.InventoryScreen
import com.pharmai.features.profile.presentation.ProfileScreen
import com.pharmai.features.reminders.presentation.RemindersScreen
import com.pharmai.features.search.presentation.DrugDetailScreen
import com.pharmai.features.scanner.presentation.ScanResultScreen

@Composable
fun PharmAiNavGraph(navController: NavHostController, startDestination: String = Destination.Home.route) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destination.Home.route) { HomeScreen(navController) }
        composable(Destination.Search.route) { SearchScreen(navController) }
        composable(Destination.Scanner.route) { CameraScreen(navController) }
        composable(Destination.Inventory.route) { InventoryScreen(navController) }
        composable(Destination.Profile.route) { ProfileScreen(navController) }
        composable(Destination.Reminders.route) { RemindersScreen(navController) }
        composable(Destination.DrugDetail.route, arguments = listOf(navArgument("drugId") { type = NavType.StringType })) {
            DrugDetailScreen(navController, it.arguments?.getString("drugId") ?: "")
        }
        composable(Destination.ScanResult.route, arguments = listOf(navArgument("scanId") { type = NavType.StringType })) {
            ScanResultScreen(navController, it.arguments?.getString("scanId") ?: "")
        }
    }
}