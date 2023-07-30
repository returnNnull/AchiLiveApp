package com.example.achiliveapp.data.models.mapper

import com.example.achiliveapp.data.models.dto.DTO
import com.example.achiliveapp.data.models.entities.BaseEntity

abstract class Mapper<E : BaseEntity, D : DTO, I : Any> {

    abstract fun toDto(e : E): D
    abstract fun toEntity(d : D) : E
}