package com.example.achiliveapp.data.repositories.hilt

import com.example.achiliveapp.data.api.RemoteSource
import com.example.achiliveapp.data.api.firebase.CategoryDataSource
import com.example.achiliveapp.data.api.firebase.RatingDataSource
import com.example.achiliveapp.data.db.source.LocalSource
import com.example.achiliveapp.data.db.source.RatingLocalSource
import com.example.achiliveapp.data.models.dto.RatingDTO
import com.example.achiliveapp.data.models.entities.RatingEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.data.repositories.utils.mapper.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RatingModule {

    @Binds
    abstract fun bindLocal(ratingLocalSource: RatingLocalSource) : LocalSource<RatingEntity, String>


    @Binds
    abstract fun bindRemote(ratingDataSource: RatingDataSource): RemoteSource<RatingDTO, String>

    @Binds
    abstract fun bindRepository(ratingRepository: ModelsRepository<RatingEntity, RatingDTO, String>) : ModelsRepository<RatingEntity, RatingDTO, String>

    @Binds
    abstract fun bindMapper(ratingMapper: Mapper<RatingEntity, RatingDTO, String>) : Mapper<RatingEntity, RatingDTO, String>
}