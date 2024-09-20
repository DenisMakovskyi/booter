package com.makovskyi.booter.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = BootEvent.TABLE_NAME)
class BootEvent(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long
) {

    companion object {

        const val TABLE_NAME = "boot_events"
    }
}
