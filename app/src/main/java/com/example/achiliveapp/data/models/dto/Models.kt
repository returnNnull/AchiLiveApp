package com.example.achiliveapp.data.models.dto

abstract class DTO<T>(
    open var id: T,
)


data class ProfileDTO(
    override var id: String = "",
    val name: String = "",
    val role: String = "USER",
    val img: String  = "",
    val motivation: String = "",
) : DTO<String>(id)

data class AwardSchemeDTO(
    override var id: String = "",
    val name: String = "",
    val about: String = "",
    var img: String = "",
    val maxValue: Int = 0,
    var categoriesId: String = "",
    val type: String = ""
) : DTO<String>(id)


data class CategoriesSchemeDTO(
    override var id: String = "",
    val name: String = "",
    val about: String = "",
    var img: String = ""
) : DTO<String>(id)


data class AwardProgressDTO (
    override var id: String = "",
    var value: Double,
    var userId: String,
    var awardSchemeId: String,
    var categorySchemeId: String,
    val isBest: Boolean,
    val endDate: String?
) : DTO<String>(id)

data class RatingDTO(
    override var id: String = "",
    var awardSchemeId: String = "",
    var categorySchemeId: String = "",
    var count: Double = 0.0,
    var sum: Double = 0.0
) : DTO<String>(id)
