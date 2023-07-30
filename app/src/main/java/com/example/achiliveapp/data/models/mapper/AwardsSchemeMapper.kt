package com.example.achiliveapp.data.models.mapper

import android.net.Uri
import com.example.achiliveapp.data.models.dto.AwardSchemeDTO
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import com.example.achiliveapp.data.models.entities.AwardType
import javax.inject.Inject

class AwardsSchemeMapper @Inject constructor() : Mapper<AwardSchemeEntity, AwardSchemeDTO, String>() {

    override fun toDto(e: AwardSchemeEntity): AwardSchemeDTO {
        return AwardSchemeDTO(
            e.id, e.name, e.about, e.img.toString(), e.maxValue, e.categoriesId, e.type.toString()
        )
    }

    override fun toEntity(d: AwardSchemeDTO): AwardSchemeEntity {

        return AwardSchemeEntity(
            d.id,
            d.name,
            d.about,
            Uri.parse(d.img),
            d.maxValue,
            d.categoriesId,
            AwardType.valueOf(d.type)
        ).apply {
            isSynchronized = true
        }
    }
}