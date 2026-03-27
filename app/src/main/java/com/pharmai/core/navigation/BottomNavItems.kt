
package com.pharmai.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Home : BottomNavItem(
        route = Screen.Home.route,
        title = "Home",
        icon = Icons.Default.Home,
        selectedIcon = Icons.Default.Home
    )

    object Search : BottomNavItem(
        route = Screen.Search.route,
        title = "Search",
        icon = Icons.Default.Search,
        selectedIcon = Icons.Default.Search
    )

    object Inventory : BottomNavItem(
        route = Screen.Inventory.route,
        title = "Cabinet",
        icon = Icons.Default.Storage,
        selectedIcon = Icons.Default.Storage
    )

    object Prescriptions : BottomNavItem(
        route = Screen.Prescriptions.route,
        title = "Rx",
        icon = Icons.Default.Receipt,
        selectedIcon = Icons.Default.Receipt
    )

    object Profile : BottomNavItem(
        route = Screen.Profile.route,
        title = "Profile",
        icon = Icons.Default.Person,
        selectedIcon = Icons.Default.Person
    )
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Search,
    BottomNavItem.Inventory,
    BottomNavItem.Prescriptions,
    BottomNavItem.Profile
)