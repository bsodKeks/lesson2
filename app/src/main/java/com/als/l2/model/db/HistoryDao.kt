package com.als.l2.model.db

import android.database.Cursor
import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE city LIKE :city")
    fun  getDataByCity(city: String): List<HistoryEntity>

    @Query("UPDATE HistoryEntity SET temperature = :temp WHERE id = :id")
    fun updateQuery(temp: Int, id: Long)

    @Query("DELETE FROM HistoryEntity WHERE city LIKE :city")
    fun deleteQuery(city: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT id, city, temperature FROM HistoryEntity")
    fun getHistoryCursor(): Cursor

    @Query("SELECT id, city, temperature FROM HistoryEntity WHERE id = :id")
    fun getHistoryCursor(id: Long): Cursor


}