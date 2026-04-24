package com.pharmai.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class SquircleShape(private val radius: Float, private val smoothness: Float = 0.6f) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            val r = radius.coerceAtMost(size.width / 2).coerceAtMost(size.height / 2)
            val s = r * smoothness
            moveTo(size.width / 2, 0f)
            cubicTo(size.width / 2 + s, 0f, size.width - s, 0f, size.width, size.height / 2 - s)
            cubicTo(size.width, size.height / 2, size.width, size.height / 2 + s, size.width - s, size.height)
            cubicTo(size.width / 2 + s, size.height, size.width / 2, size.height, 0f, size.height - s)
            cubicTo(0f, size.height, 0f, size.height / 2, s, size.height / 2)
            cubicTo(0f, size.height / 2, 0f, 0f, size.width / 2 - s, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}

object PharmAiShapes {
    val SquircleSmall = SquircleShape(12f)
    val SquircleMedium = SquircleShape(16f)
    val SquircleLarge = SquircleShape(24f)
    val RoundedSmall = RoundedCornerShape(8.dp)
    val RoundedMedium = RoundedCornerShape(12.dp)
    val RoundedLarge = RoundedCornerShape(16.dp)
    val Pill = RoundedCornerShape(50.dp)
    val DoctorProfile = SquircleMedium
    val MedicineCard = RoundedLarge
    val ButtonShape = RoundedMedium
}