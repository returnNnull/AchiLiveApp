package com.example.achiliveapp.data.models.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CategorySchemeEntity(
    id: String = "",
    val name: String = "",
    val about: String = "",
    var img: Uri = Uri.EMPTY,
    var rating: Double = 0.0

) : BaseEntity(id)