package com.example.sensingapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData

@Composable
fun LineChartComponent(modifier: Modifier = Modifier, lineData: LineData) {
    // (x,y) -> Entry -> List<Entry> -> LineDataSet -> LineData -> LineChart
    AndroidView(
        modifier = modifier,
        factory = { context ->
            LineChart(context)
                .setupLineChart()
                .apply {
                    data = lineData
                }
        },
        update = { view ->  /* Add animation here*/ }
    )
}

fun LineChart.setupLineChart(): LineChart = this.apply {
    setTouchEnabled(true)
    isDragEnabled = true
    setScaleEnabled(true)
    setPinchZoom(true)
    description.isEnabled = false

    // set up x-axis
    xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM
        // axisMinimum = -10f
        // axisMaximum = 10f
    }

    // set up y-axis
    axisLeft.apply {
//         axisMinimum = -10f
//         axisMaximum = 10f
        setDrawGridLines(false)
    }

    axisRight.isEnabled = false
}