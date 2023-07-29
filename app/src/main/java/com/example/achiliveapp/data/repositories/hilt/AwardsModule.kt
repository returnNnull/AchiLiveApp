package com.example.achiliveapp.data.repositories.hilt

import com.example.achiliveapp.data.api.RemoteSource
import com.example.achiliveapp.data.api.firebase.AwardSchemeDataSource
import com.example.achiliveapp.data.db.source.AwardSchemeLocalSource
import com.example.achiliveapp.data.db.source.LocalSource
import com.example.achiliveapp.data.models.dto.AwardSchemeDTO
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.data.repositories.utils.mapper.AwardsSchemeMapper
import com.example.achiliveapp.data.repositories.utils.mapper.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class AwardsModule {

    @Binds
    abstract fun bindAwardsLocalResource(awardSchemeLocalSource: AwardSchemeLocalSource): LocalSource<AwardSchemeEntity, String>

    @Binds
    abstract fun bindAwardsRemoteSource(awardSchemeDataSource: AwardSchemeDataSource): RemoteSource<AwardSchemeDTO, String>

    @Binds
    abstract fun bindRepository(awardsSchemeRepository: ModelsRepository<AwardSchemeEntity, AwardSchemeDTO, String>): ModelsRepository<AwardSchemeEntity, AwardSchemeDTO, String>

    @Binds
    abstract fun bindMapper(awardsSchemeMapper: AwardsSchemeMapper) : Mapper<AwardSchemeEntity, AwardSchemeDTO, String>

}