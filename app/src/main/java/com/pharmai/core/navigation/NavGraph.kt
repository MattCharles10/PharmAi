
package com.pharmai.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/*
import com.pharmai.presentation.auth.LoginScreen
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
import com.pharmai.presentation.search.SearchScreen
*/

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { fadeIn() + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        exitTransition = { fadeOut() + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { fadeIn() + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popExitTransition = { fadeOut() + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
    ) {
        // Auth Flow
        composable(Screen.Login.route) {
           // LoginScreen(navController)
        }
        composable(Screen.SignUp.route) {
          //  SignUpScreen(navController)
        }
        composable(Screen.BiometricSetup.route) {
          //  BiometricSetupScreen(navController)
        }
        composable(Screen.ForgotPassword.route) {
          //  ForgotPasswordScreen(navController)
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
            route = Screen.ReminderDetail.route,
            arguments = listOf(navArgument("reminderId") { })
        ) { backStackEntry ->
            val reminderId = backStackEntry.arguments?.getInt("reminderId") ?: return@composable
            //ReminderDetailScreen(navController, reminderId)
        }
        composable(Screen.ReminderHistory.route) {
            //  ReminderHistoryScreen(navController)
        }

        // Profile Settings
        composable(Screen.Settings.route){
           //  SettingsScreen(navController)
        }

        composable(Screen.MLSettings.route){
           //  MLSettingsScreen(navController)
        }

        // Camera/ML Screens
        composable(Screen.MedicineCamera.route) {
          //  MedicineCameraScreen
        }

        composable(Screen.ScanResult.route){
            // ScanResultScreen
        }

        composable(Screen.ScanHistory.route){
            // ScanHistoryScreen
        }



    }
}