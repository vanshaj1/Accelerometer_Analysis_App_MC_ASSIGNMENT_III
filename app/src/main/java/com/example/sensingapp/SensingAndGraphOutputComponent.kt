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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.sp
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
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
//citation:- https://github.com/aldajo92/AndroidKt_MpAndroidChartCompose
@Composable
fun SensingAndGraphOutputComponent(modifier: Modifier, x: Float,y: Float, z:Float,showGraph: Boolean,dataStored:List<AxisInfo>){
    val steps = 20
    var pointsDataX: MutableList<Float> = mutableListOf()
    var pointsDataY: MutableList<Float> = mutableListOf()
    var pointsDataZ: MutableList<Float> = mutableListOf()

    dataStored.forEach {
        pointsDataX.add(it.x.toFloat())
        pointsDataY.add(it.y.toFloat())
        pointsDataZ.add(it.z.toFloat())
    }


    val lineDataPointsX = LineData(LineDataSetCreation(pointsDataX))
    val lineDataPointsY = LineData(LineDataSetCreation(pointsDataY))
    val lineDataPointsZ = LineData(LineDataSetCreation(pointsDataZ))

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
                        ),
                        fontSize = 20.sp
                    )
                }
            Text(modifier = Modifier.padding(5.dp).background(color = Color.White),text = "For X axis:- ")
            LineChartCard(lineData = lineDataPointsX)

            Spacer(modifier = Modifier.padding(5.dp))

            Text(modifier = Modifier.padding(10.dp).background(color = Color.White),text = "For Y axis:- ")
            LineChartCard(lineData = lineDataPointsY)

            Spacer(modifier = Modifier.padding(10.dp))

            Text(modifier = Modifier.padding(10.dp).background(color = Color.White),text = "For Z axis:- ")
            LineChartCard(lineData = lineDataPointsZ)
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

fun LineDataSetCreation(listOxygenData: List<Float>): LineDataSet{
    // List<Float> -> List<Entry> -> LineDataSet
    val DataSet = listOxygenData.createDataSetWithColor(
        datasetColor = android.graphics.Color.BLUE,
        label = "Acceleration Vs Time"
    )
    return DataSet
}


fun List<Float>.createDataSetWithColor(
    datasetColor: Int = android.graphics.Color.GREEN,
    label: String = "No Label"
): LineDataSet {
    // List<Float> -> List<Entry>
    val entries = this.mapIndexed { index, value ->
        Entry(index.toFloat(), value)
    }
    // List<Entry> -> LineDataSet
    return LineDataSet(entries, label).apply {
        color = datasetColor
        setDrawFilled(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER
    }
}

@Composable
fun LineChartCard(modifier: Modifier = Modifier, lineData: LineData) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f) // (width:height) 2:1
            .padding(16.dp)
    ) {
        LineChartComponent(
            modifier = Modifier.fillMaxSize(),
            lineData = lineData
        )
    }
}