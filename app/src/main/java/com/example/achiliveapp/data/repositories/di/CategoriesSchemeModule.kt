package com.example.achiliveapp.data.repositories.di

import com.example.achiliveapp.data.api.RemoteSource
import com.example.achiliveapp.data.api.firebase.AwardSchemeFirebase
import com.example.achiliveapp.data.api.firebase.CategorySchemeFirebase
import com.example.achiliveapp.data.db.dao.IAwardSchemeDAO
import com.example.achiliveapp.data.db.dao.ICategoriesSchemeDAO
import com.example.achiliveapp.data.db.source.AwardSchemeLocalSource
import com.example.achiliveapp.data.db.source.CategoriesSchemeLocalSource
import com.example.achiliveapp.data.db.source.LocalSource
import com.example.achiliveapp.data.models.dto.AwardSchemeDTO
import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.data.models.mapper.AwardsSchemeMapper
import com.example.achiliveapp.data.models.mapper.CategoriesSchemeMapper
import com.example.achiliveapp.data.models.mapper.Mapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(ActivityComponent::class)
//abstract class CategoriesSchemeModule {
//
//    @Binds
//    abstract fun bindLocal(categoriesSchemeLocalSource: CategoriesSchemeLocalSource): LocalSource<CategorySchemeEntity, String>
//
//    @Binds
//    abstract fun bindRemote(categorySchemeFirebase: CategorySchemeFirebase): RemoteSource<CategoriesSchemeDTO, String>
//
//    @Binds
//    abstract fun bindMapper(categoryMapper: Mapper<CategorySchemeEntity, CategoriesSchemeDTO, String>) : Mapper<CategorySchemeEntity, CategoriesSchemeDTO, String>
//}


@Module
@InstallIn(SingletonComponent::class)
object CategoriesProviders{
    @Provides
    fun provideLocal(dao: ICategoriesSchemeDAO) : LocalSource<CategorySchemeEntity, String>{
        return CategoriesSchemeLocalSource(dao)
    }

    @Provides
    fun provideRemote(): RemoteSource<CategoriesSchemeDTO, String>{
        return CategorySchemeFirebase()
    }

    @Provides
    fun provideMapper(): Mapper<CategorySchemeEntity, CategoriesSchemeDTO, String> {
        return CategoriesSchemeMapper()
    }

    @Provides
    @Singleton
    fun provideRepository(
        localSource: LocalSource<CategorySchemeEntity, String>,
        remoteSource: RemoteSource<CategoriesSchemeDTO, String>,
        mapper: Mapper<CategorySchemeEntity, CategoriesSchemeDTO, String>
    ): ModelsRepository<CategorySchemeEntity, CategoriesSchemeDTO, String>{
        return ModelsRepository(localSource, remoteSource, mapper)
    }
}