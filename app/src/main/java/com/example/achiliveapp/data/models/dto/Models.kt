package com.example.achiliveapp.data.models.dto

abstract class DTO(
    var id: String,
)


class ProfileDTO(
    id: String = "",
    val name: String = "",
    val role: String = "USER",
    val img: String  = "",
    val motivation: String = "",
) : DTO(id)

class AwardSchemeDTO(
    id: String = "",
    val name: String = "",
    val about: String = "",
    var img: String = "",
    val maxValue: Int = 0,
    var categoriesId: String = "",
    val type: String = ""
) : DTO(id)


class CategoriesSchemeDTO(
    id: String = "",
    val name: String = "",
    val about: String = "",
    var img: String = ""
) : DTO(id)


class AwardProgressDTO (
    id: String = "",
    var value: Double,
    var userId: String,
    var awardSchemeId: String,
    var categorySchemeId: String,
    val isBest: Boolean,
    val endDate: String?
) : DTO(id)

class RatingDTO(
    id: String = "",
    var awardSchemeId: String = "",
    var categorySchemeId: String = "",
    var count: Double = 0.0,
    var sum: Double = 0.0
) : DTO(id)
