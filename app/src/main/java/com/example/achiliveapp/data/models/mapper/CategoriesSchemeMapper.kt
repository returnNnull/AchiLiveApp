package com.example.achiliveapp.data.models.mapper

import android.net.Uri
import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import javax.inject.Inject

class CategoriesSchemeMapper @Inject constructor()  : Mapper<CategorySchemeEntity, CategoriesSchemeDTO, String>() {
    override fun toDto(e: CategorySchemeEntity): CategoriesSchemeDTO {
        return CategoriesSchemeDTO(
            e.id,
            e.name,
            e.about,
            e.img.toString()
        )
    }

    override fun toEntity(d: CategoriesSchemeDTO): CategorySchemeEntity {
        return CategorySchemeEntity(
            d.id,
            d.name,
            d.about,
            Uri.parse(d.img)
        ).apply {
            this.isSynchronized = true
        }
    }
}