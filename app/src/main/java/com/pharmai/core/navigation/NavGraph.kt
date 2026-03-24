// app/src/main/java/com/pharmai/core/navigation/NavGraph.kt
package com.pharmai.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
/*import com.pharmai.presentation.auth.LoginScreen
import com.pharmai.presentation.auth.SignUpScreen
import com.pharmai.presentation.drugdetail.DrugDetailScreen
import com.pharmai.presentation.favorites.FavoritesScreen
import com.pharmai.presentation.home.HomeScreen
import com.pharmai.presentation.inventory.InventoryScreen
import com.pharmai.presentation.main.MainScreen
import com.pharmai.presentation.prescription.PrescriptionsScreen
import com.pharmai.presentation.prescription.ScanPrescriptionScreen
import com.pharmai.presentation.profile.ProfileScreen
import com.pharmai.presentation.reminder.RemindersScreen
import com.pharmai.presentation.search.SearchScreen */

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Auth Flow
        composable(Screen.Login.route) {
           // LoginScreen(navController)
        }
        composable(Screen.SignUp.route) {
          //  SignUpScreen(navController)
        }

        // Main App Flow
        composable(Screen.Main.route) {
           // MainScreen(navController)
        }
        composable(Screen.Home.route) {
           // HomeScreen(navController)
        }
        composable(Screen.Search.route) {
          //  SearchScreen(navController)
        }
        composable(Screen.Inventory.route) {
           // InventoryScreen(navController)
        }
        composable(Screen.Prescriptions.route) {
           // PrescriptionsScreen(navController)
        }
        composable(Screen.Profile.route) {
           // ProfileScreen(navController)
        }
        composable(Screen.Favorites.route) {
           // FavoritesScreen(navController)
        }
        composable(Screen.Reminders.route) {
           // RemindersScreen(navController)
        }

        // Drug Detail
        composable(
            route = Screen.DrugDetail.route,
            arguments = listOf(navArgument("drugId") { })
        ) { backStackEntry ->
            val drugId = backStackEntry.arguments?.getString("drugId") ?: return@composable
          //  DrugDetailScreen(navController, drugId)
        }

        // Camera/ML Screens
        composable(Screen.ScanPrescription.route) {
          //  ScanPrescriptionScreen(navController)
        }
    }
}