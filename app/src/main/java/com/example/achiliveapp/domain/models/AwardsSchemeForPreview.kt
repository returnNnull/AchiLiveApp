package com.example.achiliveapp.domain.models

import android.net.Uri

data class AwardsSchemeForPreview(
    val id: String = "",
    val name: String = "",
    val about: String = "",
    val img: Uri = Uri.EMPTY,
)