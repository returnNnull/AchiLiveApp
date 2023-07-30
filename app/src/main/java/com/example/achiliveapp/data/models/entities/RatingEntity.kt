package com.example.achiliveapp.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.achiliveapp.data.models.dto.DTO

@Entity
class RatingEntity(
        id: String = "",
        var awardSchemeId: String = "",
        var categorySchemeId: String = "",
        var count: Double = 0.0,
        var sum: Double = 0.0
) : BaseEntity(id)
