package com.pharmai.features.reminders.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FrequencySelector(
    selectedFrequency: Frequency,
    onFrequencySelected: (Frequency) -> Unit,
    modifier: Modifier = Modifier
) {
    val frequencies = Frequency.values()

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(frequencies) { frequency ->
            FilterChip(
                selected = selectedFrequency == frequency,
                onClick = { onFrequencySelected(frequency) },
                label = { Text(frequency.displayName) }
            )
        }
    }
}

enum class Frequency(val displayName: String) {
    ONCE_DAILY("Once Daily"),
    TWICE_DAILY("Twice Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    AS_NEEDED("As Needed")
}