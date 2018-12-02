package ru.nickmiller.tinkofffintech.data.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ru.nickmiller.tinkofffintech.data.entity.event.Event


@Dao
interface EventsDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): List<Event>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEvents(events: List<Event>)

}