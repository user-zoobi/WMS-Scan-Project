package com.example.scanmate.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.scanmate.data.response.LoginResponse

@Database(entities = [LoginResponse::class], version = 1, exportSchema = false)

abstract class LocalDatabase: RoomDatabase() {
    
    companion object {

        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabaseClient(context: Context) : LocalDatabase {

            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, LocalDatabase::class.java, "APP_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

    abstract fun appDao() : RoomDao
}