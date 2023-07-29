package com.example.achiliveapp.data.repositories.utils.mapper

import com.example.achiliveapp.data.models.dto.DTO
import com.example.achiliveapp.data.models.entities.BaseEntity

abstract class Mapper<E : BaseEntity<I>, D : DTO<I>, I> {

    abstract fun toDto(e : E): D
    abstract fun toEntity(d : D) : E
}