package com.lock.settings.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lock.settings.model.LockEntity

@Dao
interface MainDao {

    @Query("SELECT * FROM lock_items")
    suspend fun getAllLockItems(): List<LockEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLockItem(lockEntity: LockEntity)

    @Query("SELECT * FROM lock_items WHERE lock_name = :lock_name")
    suspend fun getLockByName(lock_name: String): List<LockEntity>

    @Query("SELECT * FROM lock_items WHERE lock_name = :lock_name")
    suspend fun update(lock_name: String): List<LockEntity>

    @Query("UPDATE lock_items SET `primary` = :primaryValue, secondary = :secondaryValue WHERE lock_name = :lockName")
    suspend fun updateByName(
        lockName: String,
        primaryValue: String?,
        secondaryValue: String?
    )

}


