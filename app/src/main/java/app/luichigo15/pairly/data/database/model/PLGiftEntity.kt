package app.luichigo15.pairly.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.luichigo15.pairly.domain.model.PLGift

@Entity(
    tableName = "gift"
)
data class PLGiftEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    @ColumnInfo(name = "expires_on") val expiresOn: Long,
    val redeemed: Boolean
) {
    fun toDomain(): PLGift = PLGift(
        id = id,
        name = name,
        expiresOn = expiresOn,
        redeemed = redeemed
    )
}
