package com.example.achiliveapp.data.repositories.utils.mapper

import com.example.achiliveapp.data.models.dto.RatingDTO
import com.example.achiliveapp.data.models.entities.RatingEntity

class RatingMapper : Mapper<RatingEntity, RatingDTO, String>() {
    override fun toDto(e: RatingEntity): RatingDTO {
        return RatingDTO(
            e.id,
            e.awardSchemeId,
            e.awardSchemeId,
            e.count,
            e.sum
        )
    }

    override fun toEntity(d: RatingDTO): RatingEntity {
        return RatingEntity(
            d.id,
            d.awardSchemeId,
            d.awardSchemeId,
            d.count,
            d.sum
        )
    }
}