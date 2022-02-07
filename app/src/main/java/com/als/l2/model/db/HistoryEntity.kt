package com.als.l2.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
const val ID = "id"
const val CITY = "city"
const val TEMPERATURE = "temperature"

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val city: String = "",
    val temperature: Int = 0,
    val conditional: String = "",
)
