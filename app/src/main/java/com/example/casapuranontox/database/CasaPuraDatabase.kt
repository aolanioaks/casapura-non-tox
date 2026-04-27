package com.example.casapuranontox.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "swaps")
data class SwapRecord(
    @PrimaryKey val productId: String,
    val productName: String,
    val category: String
)

@Dao
interface SwapDao {

    @Query("SELECT * FROM swaps")
    suspend fun getAllSwaps(): List<SwapRecord>

    @Query("SELECT COUNT(*) FROM swaps WHERE productId = :productId")
    suspend fun isSwapped(productId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSwap(swap: SwapRecord)

    @Delete
    suspend fun deleteSwap(swap: SwapRecord)
}

@Database(entities = [SwapRecord::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun swapDao(): SwapDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "casapuranontox_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}