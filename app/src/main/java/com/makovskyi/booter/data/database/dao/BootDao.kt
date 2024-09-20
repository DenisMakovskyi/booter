package com.makovskyi.booter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy

import com.makovskyi.booter.data.database.entity.BootEvent

@Dao
interface BootDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBootEvent(event: BootEvent)

    @Query("SELECT COUNT(*) FROM ${BootEvent.TABLE_NAME}")
    fun bootEventsCount(): Int

    @Query("SELECT (MAX(timestamp) - (SELECT timestamp FROM ${BootEvent.TABLE_NAME} ORDER BY timestamp DESC LIMIT 1 OFFSET 1)) FROM ${BootEvent.TABLE_NAME}")
    fun bootEventsDelta(): Long

    @Query("SELECT EXISTS(SELECT * FROM ${BootEvent.TABLE_NAME})")
    fun isBootEventExists(): Boolean
}
