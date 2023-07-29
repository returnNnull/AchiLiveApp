package com.example.achiliveapp.data.models.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class AwardSchemeEntity constructor(
    @PrimaryKey override var id: String,
    val name: String = "",
    val about: String = "",
    var img: Uri = Uri.EMPTY,
    val maxValue: Int = 0,
    var categoriesId: String = "",
    val type: AwardType = AwardType.EMPTY,
) : BaseEntity<String>(id)

enum class AwardType(val value: Int) {
    EMPTY(-1),
    NUMBERS(0),
    LOGIC(1);

    companion object {
        fun getByInt(i: Int): AwardType {
            return when (i) {
                0 -> NUMBERS
                1 -> LOGIC
                else -> EMPTY
            }
        }
    }
}