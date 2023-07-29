package com.example.achiliveapp.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


abstract class BaseEntity<I> constructor(
    open var id : I,
    var isSynchronized: Boolean = false
){
    constructor(id: I) : this(id, false)
}