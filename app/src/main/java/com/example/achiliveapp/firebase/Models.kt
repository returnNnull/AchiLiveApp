package com.example.achiliveapp.firebase

import java.util.Date

open class DTO(
    open var id: String = "",
)


data class ProfileDTO(
    val name: String = "",
    val role: String = "USER",
    val img: String  = "",
    val motivation: String = "",
) : DTO()

data class AwardSchemeDTO(
    val name: String = "",
    val about: String = "",
    var img: String = "",
    val maxValue: Int = 0,
    var categoriesId: String = "",
    val type: Int = 0
) : DTO()


data class CategoriesSchemeDTO(
    val name: String = "",
    val about: String = "",
    var img: String = "",
) : DTO()


data class AwardProgressDTO (
    var value: Double,
    var userId: String,
    var awardSchemeId: String,
    var categorySchemeId: String,
    val isBest: Boolean,
    val endDate: String?
) : DTO()
