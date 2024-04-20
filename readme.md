# Accelerometer Analysis App

# Goal of this app 
* It is to unleash the power of sensing of the accelerometer of the android phones 
* find out how frequent its value changes across different axis

# Implementation Details 
* In this app, I used accelerometer sensor to sense the forces exerted on phone by external activities
* I used sensor manager to get this sensor
* I created states such as x, y and z to display sensing values across different axis
* Whenever there is some change received by sensor, these three states get new values and whole app get recomposed
* for sensing each and every forces such as gravity and shaking, I used SensorEventListener to hear the event
* function provided by this event listener such as onSensorChanged and onAccuracyChanged has been implemented
* then changes in values of x ,y and z are being done in onSensorChanged
* while displaying the forces across the three axis , this app stores these values in the database
* database i.e. AxisDb is created using Room database dependency
* This app is having two activities one which shows forces another which show the changes in forces across time in the form of graph
* There is a button on first activity which is having text show graphs
* whenever it get clicked through intent it calls another activity which is having graphs
* these graphs are created using library called MPAndroidCharts
* On creation of the second activity by intent the values till now recorded get downloaded in the form of json file in android phones data folder. Later which can be exported from mobile to computer for further analysis 

# Citations 
* https://github.com/aldajo92/AndroidKt_MpAndroidChartCompose

# Output
*
<img height="600" src="Accelerometer Img1.jpg" width="300"/>

*
<img height="600" src="Accelerometer Img2.jpg" width="300"/>


