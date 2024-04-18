package com.example.sensingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "axisInfo")
data class AxisInfo(
    @PrimaryKey(autoGenerate = true)
    val time: Int,
    val x: Float,
    val y: Float,
    val z: Float
)