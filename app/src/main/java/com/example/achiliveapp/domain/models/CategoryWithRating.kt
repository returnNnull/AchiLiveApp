package com.example.achiliveapp.domain.models

import android.net.Uri

data class CategoryWithRating(
    val id: String = "",
    val name: String = "",
    val about: String = "",
    var img: Uri = Uri.EMPTY,
    var rating: Double = 0.0
)