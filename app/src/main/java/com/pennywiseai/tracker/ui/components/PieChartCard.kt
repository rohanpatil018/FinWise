package com.pennywiseai.tracker.ui.components

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.pennywiseai.tracker.ui.screens.analytics.CategoryData

@Composable
fun PieChartCard(
    categories: List<CategoryData>,
    modifier: Modifier = Modifier
) {
    PennyWiseCard(modifier = modifier.fillMaxWidth()) {
        val textColor = MaterialTheme.colorScheme.onSurface.toArgb()

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp), // card height
            factory = { context ->
                val chart = PieChart(context)

                // Make PieChart occupy full view
                chart.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                chart.setUsePercentValues(true)
                chart.description.isEnabled = false
                chart.setDrawEntryLabels(true)
                chart.setEntryLabelColor(Color.BLACK)
                chart.setEntryLabelTextSize(14f)
                chart.setDrawHoleEnabled(true)
                chart.holeRadius = 50f
                chart.transparentCircleRadius = 55f
                chart.setHoleColor(Color.TRANSPARENT)
                chart.setCenterText("Category Breakdown")
                chart.setCenterTextSize(18f)
                chart.setCenterTextColor(textColor)
                chart.setExtraOffsets(10f, 10f, 10f, 10f)
                chart.animateY(1000)

                // Legend
                val legend = chart.legend
                legend.textColor = textColor
                legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                legend.orientation = Legend.LegendOrientation.HORIZONTAL
                legend.textSize = 14f
                legend.isWordWrapEnabled = true
                legend.yEntrySpace = 10f
                legend.xEntrySpace = 15f

                // Pie entries
                val entries = categories.map { PieEntry(it.amount.toFloat(), it.name) }

                val dataSet = PieDataSet(entries, "")
                dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
                dataSet.valueTextColor = Color.BLACK
                dataSet.valueTextSize = 16f
                dataSet.sliceSpace = 4f
                dataSet.selectionShift = 8f

                val pieData = PieData(dataSet)
                pieData.setValueFormatter(PercentFormatter(chart))
                pieData.setValueTextSize(16f)
                pieData.setValueTextColor(Color.BLACK)

                chart.data = pieData
                chart.invalidate()
                chart
            }
        )
    }
}