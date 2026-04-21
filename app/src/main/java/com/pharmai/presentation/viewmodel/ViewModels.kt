package com.pharmai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pharmai.domain.models.*
import com.pharmai.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getInventory: GetInventoryUseCase,
    getReminders: GetRemindersUseCase,
    getExpiring: GetExpiringMedicinesUseCase
) : ViewModel() {
    val inventoryItems = getInventory().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val expiringItems = inventoryItems.map { it.filter { item -> item.status == ExpiryStatus.EXPIRING_SOON } }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val todayReminders = getReminders().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchDrugs: SearchDrugsUseCase) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Drug>>(emptyList())
    val searchResults: StateFlow<List<Drug>> = _searchResults.asStateFlow()
    fun search(query: String) { viewModelScope.launch { _searchResults.value = searchDrugs(query) } }
}

@HiltViewModel
class InventoryViewModel @Inject constructor(
    getInventory: GetInventoryUseCase,
    private val deleteItem: DeleteInventoryUseCase
) : ViewModel() {
    val items = getInventory().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun deleteItem(id: Long) { viewModelScope.launch { deleteItem(id) } }
}

@HiltViewModel
class ReminderViewModel @Inject constructor(getReminders: GetRemindersUseCase) : ViewModel() {
    val reminders = getReminders().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val recognizeMedicine: RecognizeMedicineUseCase,
    private val saveScan: GetScanHistoryUseCase
) : ViewModel() {
    // Camera logic
}