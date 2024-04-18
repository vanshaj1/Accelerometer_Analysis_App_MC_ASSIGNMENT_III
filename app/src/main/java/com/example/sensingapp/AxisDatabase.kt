package com.example.sensingapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//citation:- https://www.youtube.com/watch?v=6Z_lTWKy1lg
@Database(entities = [AxisInfo::class],version = 1)
abstract class AxisDatabase: RoomDatabase() {
    abstract fun axisInfoDao(): AxisInfoDao

    companion object{
        @Volatile
        private var DbInstance: AxisDatabase? = null

        fun getDbInstance(context: Context): AxisDatabase{
            if(DbInstance != null){
                return DbInstance as AxisDatabase
            }

            synchronized(this){
                DbInstance = Room.databaseBuilder(context.applicationContext,
                    AxisDatabase::class.java,
                    "AxisDB").build()
            }

            return DbInstance!!
        }
    }
}