package com.example.sensingapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sensingapp.ui.theme.SensingAppTheme
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
private lateinit var gson: Gson
class AnalysisActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()
        setContent {
            SensingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var dataStored by remember { mutableStateOf(listOf<AxisInfo>()) }

                     axisDB.axisInfoDao().getallAxisInfo().observe(this) { axisInfoList ->
                         dataStored = axisInfoList
                         Log.d("tar",axisInfoList.toString())
                         Log.d("TATTY", dataStored.toString())
                         Log.d("TATTY", dataStored.size.toString())
                     }
                    if(dataStored.size != 0){
                        saveDataTojson(dataStored)
                        SensingAndGraphOutputComponent(
                            modifier = Modifier,
                            x = 0f,
                            y = 0f,
                            z = 0f,
                            showGraph = true,
                            dataStored
                        )
                    }
                }
            }
        }
    }

    private fun saveDataTojson(dataStored:List<AxisInfo>){
        var json =  gson.toJson(dataStored)
        val dir = File("/data/data/com.example.sensingapp/databases/")
        val file = File(dir, "AxisdbJsonFile.json")

        var fileoutputStream: FileOutputStream? = null
        try {
            fileoutputStream = FileOutputStream(file)
            fileoutputStream.write(json?.toByteArray())
            fileoutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}