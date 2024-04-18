package com.example.sensingapp

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sensingapp.ui.theme.SensingAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.logging.Handler
import kotlin.time.Duration.Companion.seconds

lateinit var axisDB: AxisDatabase

class MainActivity : ComponentActivity() , SensorEventListener {
    lateinit var sensorManager: SensorManager
    var accelometer: Sensor? = null

    var x = mutableStateOf(0f)
    var y = mutableStateOf(0f)
    var z = mutableStateOf(0f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        axisDB = AxisDatabase.getDbInstance(applicationContext)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        deleteFromDatabase()

        setContent {
            SensingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("vanshaj",Modifier)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .width(250.dp)
                                .height(170.dp)
                            ,
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF03A9F4))
                        ) {
                            SensingAndGraphOutputComponent(
                                modifier = Modifier
                                    .padding(20.dp),
                                x = x.value,
                                y = y.value,
                                z = z.value,
                                showGraph = false,
                                ArrayList()
                            )
                            Button(
                                modifier = Modifier
                                    .padding(start=60.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White
                                    )
                                ,onClick = {
                                Intent(applicationContext, AnalysisActivity::class.java).also {
                                    startActivity(it)
                                }
                            }) {
                                Text(color = Color.Black,text = "See Graphs")
                            }
                        }
                    }
                }
            }
        }
    }

    fun deleteFromDatabase(){
        GlobalScope.launch {
            try{
                axisDB.axisInfoDao().emptyAxisTable()
                axisDB.axisInfoDao().resetCounter()
            }catch(e: Exception){
                Log.d("Error","Tattay")
            }

        }
    }

    override fun onResume() {
        super.onResume()
        accelometer.also {
            sensorManager.registerListener(this, it,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun registerSensor(){
        accelometer.also {
            sensorManager.registerListener(this, it,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterSensor(){
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val TYPE = event?.sensor?.type
         if(TYPE == Sensor.TYPE_ACCELEROMETER){
            if(event != null){
                x.value = event.values[0]
                y.value = event.values[1]
                z.value = event.values[2]
                onPause()
                GlobalScope.launch{
                    delay(1000)
                    onResume()
                }
            }
         }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SensingAppTheme {
        Greeting("Android")
    }
}