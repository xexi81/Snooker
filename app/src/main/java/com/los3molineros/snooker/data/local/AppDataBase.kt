package com.los3molineros.snooker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.los3molineros.snooker.data.model.PlayerEntity

@Database(entities = [ PlayerEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun playerDao(): PlayerDao


    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDataBase::class.java,"snooker_tables").fallbackToDestructiveMigration().build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}