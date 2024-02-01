package com.lock.settings.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lock_items")
data class LockEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "lock_name")
    val name: String? = null,

    @Embedded
    val details: Details
)


data class Details(

    @ColumnInfo(name = "primary")
    val primary: String,

    @ColumnInfo(name = "secondary")
    val secondary: String,

    @ColumnInfo(name = "defaults")
    val defaults: String
)