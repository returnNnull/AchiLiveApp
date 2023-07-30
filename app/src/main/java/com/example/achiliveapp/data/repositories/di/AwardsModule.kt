package com.example.achiliveapp.data.repositories.di

import com.example.achiliveapp.data.api.RemoteSource
import com.example.achiliveapp.data.api.firebase.AwardSchemeFirebase
import com.example.achiliveapp.data.db.dao.IAwardSchemeDAO
import com.example.achiliveapp.data.db.source.AwardSchemeLocalSource
import com.example.achiliveapp.data.db.source.LocalSource
import com.example.achiliveapp.data.models.dto.AwardSchemeDTO
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.data.models.mapper.AwardsSchemeMapper
import com.example.achiliveapp.data.models.mapper.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AwardsProviders {


    @Provides
    fun provideLocal(dao: IAwardSchemeDAO) : LocalSource<AwardSchemeEntity, String>{
        return AwardSchemeLocalSource(dao)
    }

    @Provides
    fun provideRemote() : RemoteSource<AwardSchemeDTO, String>{
        return AwardSchemeFirebase()
    }

    @Provides
    fun provideMapper(): Mapper<AwardSchemeEntity, AwardSchemeDTO, String> {
        return AwardsSchemeMapper()
    }

    @Provides
    @Singleton
    fun provideRepository(
        localSource: LocalSource<AwardSchemeEntity, String>,
        remoteSource: RemoteSource<AwardSchemeDTO, String>,
        mapper: Mapper<AwardSchemeEntity, AwardSchemeDTO, String>
    ): ModelsRepository<AwardSchemeEntity, AwardSchemeDTO, String>{
        return ModelsRepository(localSource, remoteSource, mapper)
    }


}