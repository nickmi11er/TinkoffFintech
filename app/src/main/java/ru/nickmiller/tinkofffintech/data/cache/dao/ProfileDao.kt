package ru.nickmiller.tinkofffintech.data.cache.dao

import android.arch.persistence.room.*
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile


@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile LIMIT 1")
    fun getProfile(): Profile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProfile(profile: Profile)

    @Query("DELETE FROM profile")
    fun delete()
}