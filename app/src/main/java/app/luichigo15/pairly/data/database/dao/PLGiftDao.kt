package app.luichigo15.pairly.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import app.luichigo15.common.database.dao.L15BaseDao
import app.luichigo15.pairly.data.database.model.PLGiftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PLGiftDao : L15BaseDao<PLGiftEntity> {

    @Query("SELECT * FROM gift")
    fun observeGifts(): Flow<List<PLGiftEntity>>
}