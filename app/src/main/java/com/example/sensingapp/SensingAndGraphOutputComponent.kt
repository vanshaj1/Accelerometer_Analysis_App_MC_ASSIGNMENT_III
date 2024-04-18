package com.example.sensingapp

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.checkSelfPermission
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.wavechart.WaveChart
import co.yml.charts.ui.wavechart.model.Wave
import co.yml.charts.ui.wavechart.model.WaveChartData
import co.yml.charts.ui.wavechart.model.WaveFillColor
import co.yml.charts.ui.wavechart.model.WavePlotData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


private lateinit var gson: Gson
//citation:- https://www.youtube.com/watch?v=HGsVBqUrnGY, https://github.com/codeandtheory/YCharts?tab=readme-ov-file
@Composable
fun SensingAndGraphOutputComponent(modifier: Modifier, x: Float,y: Float, z:Float,showGraph: Boolean,dataStored:List<AxisInfo>){
    val steps = 20
    var pointsDataX: MutableList<Point> = mutableListOf()
    var pointsDataY: MutableList<Point> = mutableListOf()
    var pointsDataZ: MutableList<Point> = mutableListOf()
    Log.d("TATTY2", dataStored.toString())
    dataStored.forEach {
        pointsDataX.add(Point(it.time.toFloat(),it.x))
        pointsDataY.add(Point(it.time.toFloat(),it.y))
        pointsDataZ.add(Point(it.time.toFloat(),it.z))
    }
//    val xAxisData = AxisData.Builder()
//        .axisStepSize(100.dp)
//        .backgroundColor(Color.Transparent)
//        .steps(pointsDataX.size - 1)
//        .labelData { i -> i.toString() }
//        .labelAndAxisLinePadding(15.dp)
//        .axisLineColor(MaterialTheme.colorScheme.tertiary)
//        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
//        .build()
//
//    val yAxisData = AxisData.Builder()
//        .steps(steps)
//        .backgroundColor(Color.Transparent)
//        .labelAndAxisLinePadding(20.dp)
//        .labelData { i ->
//            val yScale = 100 / steps
//            (i).toString()
//        }
//        .axisLineColor(MaterialTheme.colorScheme.tertiary)
//        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
//        .build()

//    val lineChartData1 = LineChartData(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                Line(
//                    dataPoints = pointsDataX,
//                    LineStyle(
//                        color = MaterialTheme.colorScheme.tertiary,
//                        lineType = LineType.SmoothCurve(isDotted = false)
//                    ),
//                    IntersectionPoint(
//                        color=MaterialTheme.colorScheme.tertiary
//                    ),
//                    SelectionHighlightPoint(color= MaterialTheme.colorScheme.tertiary),
//                    ShadowUnderLine(
//                        alpha = 0.5f,
//                        brush = Brush.verticalGradient(
//                            colors = listOf(
//                                MaterialTheme.colorScheme.inversePrimary,
//                                Color.Transparent
//                            )
//                        )
//                    ),
//                    SelectionHighlightPopUp()
//                )
//            ),
//        ),
//        xAxisData = xAxisData,
//        yAxisData = yAxisData,
//        gridLines = GridLines(color = MaterialTheme.colorScheme.outline),
//        backgroundColor = MaterialTheme.colorScheme.surface
//    )
//
//
//
//
//    val lineChartData2 = LineChartData(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                Line(
//                    dataPoints = pointsDataY,
//                    LineStyle(
//                        color = MaterialTheme.colorScheme.tertiary,
//                        lineType = LineType.SmoothCurve(isDotted = false)
//                    ),
//                    IntersectionPoint(
//                        color=MaterialTheme.colorScheme.tertiary
//                    ),
//                    SelectionHighlightPoint(color= MaterialTheme.colorScheme.tertiary),
//                    ShadowUnderLine(
//                        alpha = 0.5f,
//                        brush = Brush.verticalGradient(
//                            colors = listOf(
//                                MaterialTheme.colorScheme.inversePrimary,
//                                Color.Transparent
//                            )
//                        )
//                    ),
//                    SelectionHighlightPopUp()
//                )
//            ),
//        ),
//        xAxisData = xAxisData,
//        yAxisData = yAxisData,
//        gridLines = GridLines(color = MaterialTheme.colorScheme.outline),
//        backgroundColor = MaterialTheme.colorScheme.surface
//    )
//
//
//    val lineChartData3 = LineChartData(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                Line(
//                    dataPoints = pointsDataZ,
//                    LineStyle(
//                        color = MaterialTheme.colorScheme.tertiary,
//                        lineType = LineType.SmoothCurve(isDotted = false)
//                    ),
//                    IntersectionPoint(
//                        color=MaterialTheme.colorScheme.tertiary
//                    ),
//                    SelectionHighlightPoint(color= MaterialTheme.colorScheme.tertiary),
//                    ShadowUnderLine(
//                        alpha = 0.5f,
//                        brush = Brush.verticalGradient(
//                            colors = listOf(
//                                MaterialTheme.colorScheme.inversePrimary,
//                                Color.Transparent
//                            )
//                        )
//                    ),
//                    SelectionHighlightPopUp()
//                )
//            ),
//        ),
//        xAxisData = xAxisData,
//        yAxisData = yAxisData,
//        gridLines = GridLines(color = MaterialTheme.colorScheme.outline),
//        backgroundColor = MaterialTheme.colorScheme.surface
//    )


    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(pointsDataX.size - 1)
        .labelData { index -> index.toString() }
        .build()
    val nums = arrayOf(-4, 3,2,1,0,1,2,3,4)
    val yAxisData = AxisData.Builder()
        .steps(8)
        .axisStepSize(1.dp)
        .labelAndAxisLinePadding(20.dp)
        .labelData { index ->  nums.get(index).toString() }
        .build()


    val waveChartData = WaveChartData(
        wavePlotData = WavePlotData(
            lines = listOf(
                Wave(
                    dataPoints = pointsDataX,
                    waveStyle = LineStyle(color = Color.Black),
                    selectionHighlightPoint = SelectionHighlightPoint(),
                    shadowUnderLine = ShadowUnderLine(),
                    selectionHighlightPopUp = SelectionHighlightPopUp(),
                    waveFillColor = WaveFillColor(topColor = Color.Green, bottomColor = Color.Red),
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines()
    )

    Column (
        modifier = modifier.conditional(showGraph){
            background(Color(0xFF03A9F4))
        }
    ){
        if(showGraph == false){
            Text(color = Color.White,text = "X axis:- $x")
            Text(color = Color.White,text = "Y axis:- $y")
            Text(color = Color.White,text = "Z axis:- $z")
            SaveToDatabase(0,x,y,z)
        }else{

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "Accelerometer Data analysis"
                        ,style= TextStyle(
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )
                }

//            Text(modifier = Modifier
//                    .paddingFromBaseline(top = 50.dp)
//                    .padding(start=95.dp),
//                text = "Accelerometer Data analysis",
//                textAlign = TextAlign.Center,
//                style= TextStyle(
//                    background = Color(android.graphics.Color.parseColor("#A92EF5")),
//                    color = Color.White
//                )
////            )
//            LineChart(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(450.dp),
//                lineChartData = lineChartData1
//            )
//            LineChart(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(250.dp),
//                lineChartData = lineChartData2
//            )
//            LineChart(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(250.dp),
//                lineChartData = lineChartData3
//            )
            WaveChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                waveChartData = waveChartData
            )
        }
    }
}

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition == true) {
        then(modifier(Modifier))
    } else {
        this
    }
}


fun SaveToDatabase(time:Int,x:Float,y:Float,z:Float){
    GlobalScope.launch {
        try{
            axisDB.axisInfoDao().insertAxisInfo(AxisInfo(time,x,y,z))
        }catch(e: Exception){
            Log.d("Error","Tattay")
        }

    }
}

//fun loadDataFromDatabase(time: Int){
//    GlobalScope.launch {
//        var temp: AxisInfo? = null
//        temp = async {
//            axisDB.axisInfoDao().getAxisInfoByTime(time)
//        }.await()
//    }
//}