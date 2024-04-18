package com.example.sensingapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AxisInfoDao {

    @Insert
    suspend fun insertAxisInfo(axisInfo: AxisInfo)


    @Query("SELECT time,x,y,z FROM axisInfo LIMIT 100")
    fun getallAxisInfo(): LiveData<List<AxisInfo>>


    @Query("Delete FROM axisInfo")
    suspend fun emptyAxisTable()

    @Query("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'axisInfo'")
    suspend fun resetCounter()
}