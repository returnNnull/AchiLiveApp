package com.example.achiliveapp.data.repositories.di

import com.example.achiliveapp.data.api.RemoteSource
import com.example.achiliveapp.data.api.firebase.RatingFirebase
import com.example.achiliveapp.data.db.dao.IRatingDAO
import com.example.achiliveapp.data.db.source.LocalSource
import com.example.achiliveapp.data.db.source.RatingLocalSource
import com.example.achiliveapp.data.models.dto.RatingDTO
import com.example.achiliveapp.data.models.entities.RatingEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.data.models.mapper.Mapper
import com.example.achiliveapp.data.models.mapper.RatingMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RatingModules {

    @Provides
    fun provideLocal(dao: IRatingDAO): LocalSource<RatingEntity, String> {
        return RatingLocalSource(dao)
    }

    @Provides
    fun provideRemote(): RemoteSource<RatingDTO, String> {
        return RatingFirebase()
    }

    @Provides
    fun provideMapper(): Mapper<RatingEntity, RatingDTO, String> {
        return RatingMapper()
    }

    @Provides
    fun provideRepository(
        localSource: LocalSource<RatingEntity, String>,
        remoteSource: RemoteSource<RatingDTO, String>,
        mapper: Mapper<RatingEntity, RatingDTO, String>
    ): ModelsRepository<RatingEntity, RatingDTO, String> {
        return ModelsRepository(localSource, remoteSource, mapper)
    }

}