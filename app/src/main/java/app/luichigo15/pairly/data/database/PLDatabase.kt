package app.luichigo15.pairly.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.luichigo15.pairly.data.database.dao.PLGiftDao
import app.luichigo15.pairly.data.database.model.PLGiftEntity

@Database(
    entities = [PLGiftEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class PLDatabase : RoomDatabase() {

    abstract fun giftDao(): PLGiftDao

}