package com.als.l2.model.db

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE city LIKE :city")
    fun  getDataByCity(city: String): List<HistoryEntity>

    @Query("UPDATE HistoryEntity SET `temp` = :temp WHERE id = :id")
    fun updateQuery(temp: Int, id: Long)

    @Query("DELETE FROM HistoryEntity WHERE city LIKE :city")
    fun deleteQuery(city: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)
}