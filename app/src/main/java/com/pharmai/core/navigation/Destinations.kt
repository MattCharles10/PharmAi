package com.pharmai.core.navigation

sealed class Destination(val route: String) {
    // Auth
    data object Login : Destination("login")
    data object SignUp : Destination("signup")
    data object BiometricSetup : Destination("biometric_setup")
    data object ForgotPassword : Destination("forgot_password")

    // Main Tabs
    data object Home : Destination("home")
    data object Search : Destination("search")
    data object Scanner : Destination("scanner")
    data object Inventory : Destination("inventory")
    data object Profile : Destination("profile")

    // Feature Screens
    data object Reminders : Destination("reminders")
    data object Prescriptions : Destination("prescriptions")
    data object ScanHistory : Destination("scan_history")
    data object Settings : Destination("settings")
    data object Favorites : Destination("favorites")
    data object Consultation : Destination("consultation")
    data object Doctors : Destination("doctors")
    data object ML_Settings : Destination("ml_settings")

    // Detail Screens (with arguments)
    data object DrugDetail : Destination("drug_detail/{drugId}") {
        fun createRoute(drugId: String) = "drug_detail/$drugId"
    }
    data object MedicineDetail : Destination("medicine_detail/{medicineId}") {
        fun createRoute(medicineId: Long) = "medicine_detail/$medicineId"
    }
    data object ScanResult : Destination("scan_result/{scanId}") {
        fun createRoute(scanId: String) = "scan_result/$scanId"
    }
    data object CreateReminder : Destination("create_reminder/{medicineId}") {
        fun createRoute(medicineId: Long? = null) = "create_reminder/${medicineId ?: ""}"
    }
    data object ReminderDetail : Destination("reminder_detail/{reminderId}") {
        fun createRoute(reminderId: Long) = "reminder_detail/$reminderId"
        fun createRoute(medicineId: Long? = null) = "create_reminder/${medicineId ?: ""}"

    }
    data object AddMedicine : Destination("add_medicine")
    data object EditMedicine : Destination("edit_medicine/{medicineId}") {
        fun createRoute(medicineId: Long) = "edit_medicine/$medicineId"
    }
    data object AddPrescription : Destination("add_prescription")
    data object PrescriptionDetail : Destination("prescription_detail/{prescriptionId}") {
        fun createRoute(prescriptionId: Long) = "prescription_detail/$prescriptionId"
    }
    data object ExpiryAlerts : Destination("expiry_alerts")
    data object ReminderHistory : Destination("reminder_history")


}