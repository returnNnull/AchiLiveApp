package com.example.achiliveapp.share

data class SpinnerItem(
    val id: String = "",
    val name: String = "",
    val position: Int
) {
    override fun toString(): String {
        return name
    }

    companion object{

        fun createPlaceholder(): SpinnerItem{
            return SpinnerItem(name = "Выбрать категорию", position = 0)
        }
    }
}