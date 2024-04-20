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

# Prediction of next 10 sec changes in accelerometer across three angles using previous data
* I collected 100 values of each angles across four different intervals of accelerometer such as DELAY_NORMAL, DELAY_UI, DELAY_GAME and 10000 ms
* I used linear regression to predict next ten second values for the three axis across these four intervals and plot the following graphs of actual vs predicted values

#### DELAY_NORMAL 
#####  For x axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/Normal Delay/x axis.png" width="600"/>
* Models Mean squared error is :- 119
##### For y axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/Normal Delay/y axis.png" width="600"/>
* Models Mean squared error is :- 176
##### For z axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/Normal Delay/z axis.png" width="600"/>
* Models Mean squared error is :- 302
#### DELAY_UI
#####  For x axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/UI Delay/x axis.png" width="600"/>
* Models Mean squared error is :- 6.76
##### For y axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/UI Delay/y axis.png" width="600"/>
* Models Mean squared error is :- 78.095
##### For z axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/UI Delay/z axis.png" width="600"/>
* Models Mean squared error is :- 120

#### DELAY_GAME
##### For x axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/GAME Delay/x axis.png" width="600"/>
* Models Mean squared error is :- 99
##### For y axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/GAME Delay/y axis.png" width="600"/>
* Models Mean squared error is :- 148
##### For z axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/GAME Delay/z axis.png" width="600"/>
* Models Mean squared error is :- 141
#### DELAY_10000
##### For x axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/10000 Delay/x axis.png" width="600"/>
* Models Mean squared error is :- 30
##### For y axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/10000 Delay/y axis.png" width="600"/>
* Models Mean squared error is :- 315
##### For z axis
* <img height="600" src="Assignment 3 Helpers/Prediction 10s  and Comparison Graphs/change Interval/10000 Delay/z axis.png" width="600"/>
* Models Mean squared error is :- 93

##### Note
* These Mean square errors are quite high but it is because of the no of samples on which model is trained on. 
* I trained linear regression on 90 samples only. So I think according to this no, these nos are reasonably good

  
