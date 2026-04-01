package com.pharmai.presentation.components.cards


import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.pharmai.domain.model.UserInventory
import com.pharmai.core.common.utils.DateUtils
import com.pharmai.presentation.components.common.NetworkImage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InventoryCard(
    medicine: UserInventory,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val expiryStatus = getExpiryStatus(medicine.expiryDate)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Medicine Image
                    NetworkImage(
                        url = medicine.imageUrl,
                        contentDescription = medicine.name,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Medicine Info
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = medicine.name,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${medicine.dosage} • ${medicine.quantity} ${medicine.unit}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Expiry Status Badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = expiryStatus.color.copy(alpha = 0.1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = expiryStatus.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp),
                                    tint = expiryStatus.color
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = expiryStatus.text,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = expiryStatus.color
                                )
                                )
                            }
                        }
                    }

                    // Menu Button
                    Box {
                        IconButton(
                            onClick = { expanded = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Options"
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit") },
                                onClick = {
                                    expanded = false
                                    onEdit()
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Delete") },
                                onClick = {
                                    expanded = false
                                    onDelete()
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Additional Details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailChip(
                        icon = Icons.Default.DateRange,
                        text = DateUtils.formatDate(medicine.purchaseDate),
                        label = "Purchased"
                    )
                    medicine.batchNumber?.let {
                        DetailChip(
                            icon = Icons.Default.Info,
                            text = it,
                            label = "Batch"
                        )
                    }
                }

                // Expandable Section
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Divider()
                        Spacer(modifier = Modifier.height(12.dp))

                        medicine.instructions?.let {
                            Text(
                                text = "Instructions:",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        medicine.manufacturer?.let {
                            Text(
                                text = "Manufacturer: $it",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        medicine.notes?.let {
                            Text(
                                text = "Notes: $it",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DetailChip(
        icon: androidx.compose.ui.graphics.vector.ImageVector,
        text: String,
        label: String,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    data class ExpiryStatus(
        val text: String,
        val color: Color,
        val icon: androidx.compose.ui.graphics.vector.ImageVector
    )

    fun getExpiryStatus(expiryDate: java.util.Date?): ExpiryStatus {
        return if (expiryDate == null) {
            ExpiryStatus(
                text = "No expiry date",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                icon = Icons.Default.Info
            )
        } else {
            val daysUntilExpiry = DateUtils.daysUntilExpiry(expiryDate)
            when {
                daysUntilExpiry < 0 -> ExpiryStatus(
                    text = "Expired",
                    color = MaterialTheme.colorScheme.error,
                    icon = Icons.Default.Warning
                )
                daysUntilExpiry <= 30 -> ExpiryStatus(
                    text = "Expires in $daysUntilExpiry days",
                    color = MaterialTheme.colorScheme.error,
                    icon = Icons.Default.Warning
                )
                daysUntilExpiry <= 90 -> ExpiryStatus(
                    text = "Expires in $daysUntilExpiry days",
                    color = MaterialTheme.colorScheme.tertiary,
                    icon = Icons.Default.Info
                )
                else -> ExpiryStatus(
                    text = "Valid",
                    color = MaterialTheme.colorScheme.primary,
                    icon = Icons.Default.CheckCircle
                )
            }
        }
    }