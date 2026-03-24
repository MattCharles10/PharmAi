
package com.pharmai.core.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object BiometricSetup : Screen("biometric_setup")

    object Main : Screen("main")
    object Home : Screen("home")
    object Inventory : Screen("inventory")
    object Prescriptions : Screen("prescriptions")
    object Reminders : Screen("reminders")
    object Search : Screen("search")
    object Profile : Screen("profile")
    object Favorites : Screen("favorites")

    object DrugDetail : Screen("drug_detail/{drugId}") {
        fun passId(drugId: String) = "drug_detail/$drugId"
    }

    object AddToInventory : Screen("add_to_inventory")
    object EditInventory : Screen("edit_inventory/{inventoryId}") {
        fun passId(inventoryId: Int) = "edit_inventory/$inventoryId"
    }

    object ScanBarcode : Screen("scan_barcode")
    object ScanPrescription : Screen("scan_prescription")
    object MedicineCamera : Screen("medicine_camera")
    object ScanResult : Screen("scan_result/{scanId}") {
        fun passId(scanId: String) = "scan_result/$scanId"
    }
    object ScanHistory : Screen("scan_history")

    object AddPrescription : Screen("add_prescription")
    object PrescriptionDetail : Screen("prescription_detail/{prescriptionId}") {
        fun passId(prescriptionId: Int) = "prescription_detail/$prescriptionId"
    }

    object CreateReminder : Screen("create_reminder")
    object ReminderDetail : Screen("reminder_detail/{reminderId}") {
        fun passId(reminderId: Int) = "reminder_detail/$reminderId"
    }

    object Settings : Screen("settings")
    object MLSettings : Screen("ml_settings")
}