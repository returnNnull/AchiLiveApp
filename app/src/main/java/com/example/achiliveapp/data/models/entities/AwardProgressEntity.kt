package com.example.achiliveapp.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.achiliveapp.data.models.dto.DTO


@Entity
class AwardProgressEntity(
    id: String,
    var value: Double,
    var userId: String,
    var awardSchemeId: String,
    var categorySchemeId: String,
    val isBest: Boolean,
    val endDate: String?
) : BaseEntity(id)