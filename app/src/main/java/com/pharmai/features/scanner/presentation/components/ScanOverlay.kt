package com.pharmai.features.scanner.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ScanOverlay(
    modifier: Modifier = Modifier,
    isScanning: Boolean = false
) {
    // Get the color OUTSIDE Canvas lambda
    val bracketColor = MaterialTheme.colorScheme.primary
    val textColor = Color.White

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val scanBoxSize = size.minDimension * 0.7f
            val scanBoxOffset = Offset(
                (size.width - scanBoxSize) / 2,
                (size.height - scanBoxSize) / 2
            )

            // Semi-transparent background
            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                size = size
            )

            // Clear scan area
            drawRect(
                color = Color.Transparent,
                topLeft = scanBoxOffset,
                size = Size(scanBoxSize, scanBoxSize),
                blendMode = androidx.compose.ui.graphics.BlendMode.Clear
            )

            // Scan box border
            drawRoundRect(
                color = Color.White,
                topLeft = scanBoxOffset,
                size = Size(scanBoxSize, scanBoxSize),
                cornerRadius = CornerRadius(16.dp.toPx()),
                style = Stroke(width = 4.dp.toPx())
            )

            // Corner brackets - Use the color captured outside
            val bracketLength = 40.dp.toPx()

            // Top-left
            drawLine(bracketColor, scanBoxOffset, scanBoxOffset.copy(x = scanBoxOffset.x + bracketLength), strokeWidth = 4.dp.toPx())
            drawLine(bracketColor, scanBoxOffset, scanBoxOffset.copy(y = scanBoxOffset.y + bracketLength), strokeWidth = 4.dp.toPx())

            // Top-right
            val topRight = scanBoxOffset.copy(x = scanBoxOffset.x + scanBoxSize)
            drawLine(bracketColor, topRight, topRight.copy(x = topRight.x - bracketLength), strokeWidth = 4.dp.toPx())
            drawLine(bracketColor, topRight, topRight.copy(y = topRight.y + bracketLength), strokeWidth = 4.dp.toPx())

            // Bottom-left
            val bottomLeft = scanBoxOffset.copy(y = scanBoxOffset.y + scanBoxSize)
            drawLine(bracketColor, bottomLeft, bottomLeft.copy(x = bottomLeft.x + bracketLength), strokeWidth = 4.dp.toPx())
            drawLine(bracketColor, bottomLeft, bottomLeft.copy(y = bottomLeft.y - bracketLength), strokeWidth = 4.dp.toPx())

            // Bottom-right
            val bottomRight = scanBoxOffset.copy(x = scanBoxOffset.x + scanBoxSize, y = scanBoxOffset.y + scanBoxSize)
            drawLine(bracketColor, bottomRight, bottomRight.copy(x = bottomRight.x - bracketLength), strokeWidth = 4.dp.toPx())
            drawLine(bracketColor, bottomRight, bottomRight.copy(y = bottomRight.y - bracketLength), strokeWidth = 4.dp.toPx())
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isScanning) "Scanning..." else "Position medicine within frame",
                color = textColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}