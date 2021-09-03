package kz.yshop.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kz.yshop.data.remote.responses.Demo.Data

@Database(
    entities = [Data::class],
    version = 1,
    exportSchema = false
)

abstract class YShopDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var instance: YShopDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Application) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Application) = Room.databaseBuilder(
            context.applicationContext
            , YShopDatabase::class.java
            , "YShopDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}