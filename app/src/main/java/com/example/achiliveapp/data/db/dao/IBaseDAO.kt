package com.example.achiliveapp.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.achiliveapp.data.models.entities.BaseEntity


interface IBaseDAO<E : BaseEntity<String>> {

    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg e : E)

    @Delete
    suspend fun delete(e: E)
}