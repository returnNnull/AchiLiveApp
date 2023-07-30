package com.example.achiliveapp.data.models.mapper

import com.example.achiliveapp.data.models.dto.RatingDTO
import com.example.achiliveapp.data.models.entities.RatingEntity
import javax.inject.Inject

class RatingMapper @Inject constructor() : Mapper<RatingEntity, RatingDTO, String>() {
    override fun toDto(e: RatingEntity): RatingDTO {
        return RatingDTO(
            e.id,
            e.awardSchemeId,
            e.categorySchemeId,
            e.count,
            e.sum
        )
    }

    override fun toEntity(d: RatingDTO): RatingEntity {
        return RatingEntity(
            d.id,
            d.awardSchemeId,
            d.categorySchemeId,
            d.count,
            d.sum
        )
    }
}