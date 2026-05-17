package com.ananya.rakshakavach.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Incident::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun incidentDao(): IncidentDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "raksha_kavach_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}