package com.example.achiliveapp.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


abstract class BaseEntity constructor(
    @PrimaryKey var id : String,
    var isSynchronized: Boolean = false
)