package com.makovskyi.booter.data.database

import android.content.Context

import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase

import com.makovskyi.booter.data.database.dao.BootDao
import com.makovskyi.booter.data.database.entity.BootEvent

@Database(
    version = AppDatabase.VERSION,
    entities = [BootEvent::class]
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        const val VERSION = 1
        const val DB_NAME = "AppDatabase.db"

        private var INSTANCE: AppDatabase? = null

        operator fun invoke(context: Context): AppDatabase =
            synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getBootDao(): BootDao
}
