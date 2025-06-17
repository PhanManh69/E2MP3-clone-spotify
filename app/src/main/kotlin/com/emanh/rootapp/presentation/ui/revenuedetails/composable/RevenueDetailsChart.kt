package com.emanh.rootapp.presentation.ui.revenuedetails.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfaceBackgroundLight
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceProductDark
import com.emanh.rootapp.presentation.theme.SurfaceSecondaryInvert
import com.emanh.rootapp.presentation.theme.TextPrimary

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun RevenueDetailsChart(
    modifier: Modifier = Modifier,
    data: List<Float>,
    strokeWidth: Float = 4f,
    pointRadius: Float = 8f,
    horizontalLineWidth: Float = 1f,
    showPoints: Boolean = true,
    showHorizontalLines: Boolean = true,
    lineColor: Color = SurfaceProduct,
    pointColor: Color = SurfaceProductDark,
    horizontalLineColor: Color = SurfaceSecondaryInvert,
) {
    BoxWithConstraints(modifier = modifier) {
        val containerHeight = maxHeight

        Row {
            VerticalDivider(modifier = Modifier
                .height(containerHeight)
                .padding(vertical = 16.dp), color = SurfaceBackgroundLight, thickness = 1.dp)

            Canvas(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)) {
                if (data.size < 2) return@Canvas

                val linePath = Path()
                val fillPath = Path()
                val width = size.width
                val height = size.height
                val padding = 50f
                val chartWidth = width - 2 * padding
                val chartHeight = height - 2 * padding
                val minValue = data.minOrNull() ?: 0f
                val maxValue = data.maxOrNull() ?: 1f
                val valueRange = maxValue - minValue
                val points = data.mapIndexed { index, value ->
                    val x = padding + (index.toFloat() / (data.size - 1)) * chartWidth
                    val y = padding + chartHeight - ((value - minValue) / valueRange) * chartHeight
                    Offset(x, y)
                }

                if (points.isNotEmpty()) {
                    linePath.moveTo(points[0].x, points[0].y)
                    fillPath.moveTo(points[0].x, padding + chartHeight)
                    fillPath.lineTo(points[0].x, points[0].y)

                    for (i in 1 until points.size) {
                        val currentPoint = points[i]
                        val previousPoint = points[i - 1]
                        val distance = (currentPoint.x - previousPoint.x) * 0.4f
                        val controlPoint1 = Offset(previousPoint.x + distance, previousPoint.y)
                        val controlPoint2 = Offset(currentPoint.x - distance, currentPoint.y)

                        linePath.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, currentPoint.x, currentPoint.y)
                        fillPath.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, currentPoint.x, currentPoint.y)
                    }

                    fillPath.lineTo(points.last().x, padding + chartHeight)
                    fillPath.lineTo(points[0].x, padding + chartHeight)
                    fillPath.close()
                }

                val gradientBrush = Brush.verticalGradient(
                        colors = listOf(lineColor.copy(alpha = 0.6f), lineColor.copy(alpha = 0.1f), Color.Transparent),
                        startY = padding,
                        endY = padding + chartHeight,
                )

                drawPath(path = fillPath, brush = gradientBrush)
                drawPath(path = linePath, color = lineColor, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))

                if (showHorizontalLines) {
                    points.forEach { point ->
                        drawLine(color = horizontalLineColor,
                                 start = Offset(point.x, point.y),
                                 end = Offset(width, point.y),
                                 strokeWidth = horizontalLineWidth,
                                 pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f), 0f))
                    }
                }

                if (showPoints) {
                    points.forEach { point ->
                        drawCircle(color = pointColor, radius = pointRadius, center = point)
                    }
                }
            }

            Box(modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxHeight()) {
                data.forEachIndexed { index, item ->
                    val minValue = data.minOrNull() ?: 0f
                    val maxValue = data.maxOrNull() ?: 1f
                    val valueRange = maxValue - minValue
                    val padding = 50f
                    val density = LocalDensity.current
                    val paddingDp = with(density) { padding.toDp() }
                    val chartHeightDp = containerHeight - (paddingDp * 2)
                    val yPositionRatio = (item - minValue) / valueRange
                    val yOffsetDp = paddingDp + chartHeightDp * (1f - yPositionRatio)

                    Text(text = "$item",
                         color = TextPrimary,
                         style = Body6Regular,
                         modifier = Modifier
                             .offset(y = yOffsetDp - 8.dp)
                             .wrapContentSize())
                }
            }
        }
    }
}

@Preview
@Composable
private fun RevenueDetailsChartPreview() {
    E2MP3Theme {
        val sampleData = listOf(10f, 25.7f, 20.5f, 30f, 45f, 40f, 53.2f, 60f, 55.1f, 70f, 65f, 80.2f)

        RevenueDetailsChart(modifier = Modifier.height(600.dp), data = sampleData)
    }
}