package com.pharmai.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pharmai.features.auth.presentation.LoginScreen
import com.pharmai.features.auth.presentation.SignUpScreen
import com.pharmai.features.home.presentation.HomeScreen
import com.pharmai.features.inventory.presentation.AddMedicineScreen
import com.pharmai.features.inventory.presentation.InventoryScreen
import com.pharmai.features.inventory.presentation.MedicineDetailScreen
import com.pharmai.features.prescriptions.presentation.AddPrescriptionScreen
import com.pharmai.features.prescriptions.presentation.PrescriptionsScreen
import com.pharmai.features.profile.presentation.ProfileScreen
import com.pharmai.features.profile.presentation.SettingsScreen
import com.pharmai.features.reminders.presentation.CreateReminderScreen
import com.pharmai.features.reminders.presentation.RemindersScreen
import com.pharmai.features.scanner.presentation.CameraScreen
import com.pharmai.features.scanner.presentation.ScanHistoryScreen
import com.pharmai.features.scanner.presentation.ScanResultScreen
import com.pharmai.features.search.presentation.DrugDetailScreen
import com.pharmai.features.search.presentation.SearchScreen

@Composable
fun PharmAiNavGraph(
    navController: NavHostController,
    startDestination: String = Destination.Home.route
) {
    NavHost(navController = navController, startDestination = startDestination) {

        // Auth Screens
        composable(Destination.Login.route) { LoginScreen(navController) }
        composable(Destination.SignUp.route) { SignUpScreen(navController) }

        // Main Tabs
        composable(Destination.Home.route) { HomeScreen(navController) }
        composable(Destination.Search.route) { SearchScreen(navController) }
        composable(Destination.Scanner.route) { CameraScreen(navController) }
        composable(Destination.Inventory.route) { InventoryScreen(navController) }
        composable(Destination.Profile.route) { ProfileScreen(navController) }

        // Feature Screens
        composable(Destination.Reminders.route) { RemindersScreen(navController) }
        composable(Destination.Prescriptions.route) { PrescriptionsScreen(navController) }
        composable(Destination.Settings.route) { SettingsScreen(navController) }
        composable(Destination.ScanHistory.route) { ScanHistoryScreen(navController) }
        composable(Destination.Favorites.route) { /* FavoritesScreen */ }

        // Add Screens
        composable(Destination.AddMedicine.route) { AddMedicineScreen(navController) }
        composable(Destination.AddPrescription.route) { AddPrescriptionScreen(navController) }

        // Create Reminder
        composable(
            route = "create_reminder/{medicineId}",
            arguments = listOf(
                navArgument("medicineId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val medicineId = backStackEntry.arguments?.getString("medicineId")?.toLongOrNull()
            CreateReminderScreen(navController, medicineId)
        }

        // Drug Detail
        composable(
            route = Destination.DrugDetail.route,
            arguments = listOf(navArgument("drugId") { type = NavType.StringType })
        ) { backStackEntry ->
            DrugDetailScreen(navController, backStackEntry.arguments?.getString("drugId") ?: "")
        }

        // Medicine Detail
        composable(
            route = Destination.MedicineDetail.route,
            arguments = listOf(navArgument("medicineId") { type = NavType.LongType })
        ) { backStackEntry ->
            MedicineDetailScreen(backStackEntry.arguments?.getLong("medicineId") ?: 0L, navController)
        }

        // Scan Result
        composable(
            route = Destination.ScanResult.route,
            arguments = listOf(navArgument("scanId") { type = NavType.StringType })
        ) { backStackEntry ->
            ScanResultScreen(navController, backStackEntry.arguments?.getString("scanId") ?: "")
        }
    }
}