package com.example.achiliveapp.main.achi.data

import android.net.Uri

data class AwardScheme(
    val id: String = "",
    val name: String = "",
    val about: String = "",
    var img: Uri = Uri.EMPTY,
    val maxValue: Int = 0,
    var categoriesId: String = "",
    val type: Type = Type.LOGIC
)
{

    enum class Type(val value: Int) {
        EMPTY(-1),
        NUMBERS(0),
        LOGIC(1);

        companion object{
            fun getByInt(i: Int): Type{
                return when(i){
                    0 -> NUMBERS
                    1 -> LOGIC
                    else -> EMPTY
                }
            }
        }
    }

}