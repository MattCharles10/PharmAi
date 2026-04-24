package com.pharmai.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun PharmAiBottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(Destination.Home.route, "Home", Icons.Outlined.Home, Icons.Filled.Home),
        BottomNavItem(Destination.Search.route, "Search", Icons.Outlined.Search, Icons.Filled.Search),
        BottomNavItem(Destination.Scanner.route, "Scan", Icons.Outlined.QrCodeScanner, Icons.Filled.QrCodeScanner),
        BottomNavItem(Destination.Inventory.route, "Inventory", Icons.Outlined.Inventory, Icons.Filled.Inventory),
        BottomNavItem(Destination.Profile.route, "Profile", Icons.Outlined.Person, Icons.Filled.Person)
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = { Icon(if (selected) item.selectedIcon else item.unselectedIcon, item.label) },
                label = { Text(item.label) },
                selected = selected,
                onClick = { navController.navigate(item.route) { popUpTo(Destination.Home.route); launchSingleTop = true } }
            )
        }
    }
}

private data class BottomNavItem(val route: String, val label: String, val unselectedIcon: ImageVector, val selectedIcon: ImageVector)